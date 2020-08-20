package io.ian.demo.service.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import io.ian.demo.core.shardingjdbc.ShardingUtils;
import io.ian.demo.core.util.DataUtils;
import io.ian.demo.core.util.DateUtils;
import io.ian.demo.service.entity.Order;
import io.ian.demo.service.dao.OrderMapper;
import io.ian.demo.service.entity.OrderItem;
import io.ian.demo.service.service.OrderItemService;
import io.ian.demo.service.service.OrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author XHD
 * @since 2020-07-16
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderItemService orderItemService;

    @Override
    public void initData() {
        //主表50万 从表50*2=100万
        int count = 500000;
        //每次入库20条
        int size = 20;
        List<Order> tempList = new ArrayList<>();
        List<OrderItem> orderItemList = new ArrayList<>();
        while (count > 0) {
            addBatch(count, size, tempList, orderItemList);
            count -= size;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Page<Order> listPage(String bgtm, String edtm, Integer current, Integer size, String userName) {
        Page<Order> pageParam = new Page<>();
        pageParam.setCurrent(current);
        if (null != size) {
            pageParam.setSize(size);
        }
        Wrapper<Order> ew = new EntityWrapper<Order>()
                .between("create_time", DateUtils.parseDate(bgtm), DateUtils.getEndTimeOfDay(edtm))
                .orderBy("create_time", false);
        if (StringUtils.isNotBlank(userName)) {
            ew.eq("user_name", userName);
        }
        Page<Order> page = selectPage(pageParam, ew);
        Wrapper<OrderItem> orderItemWrapper = new EntityWrapper<OrderItem>()
                .between("order_create_time", DateUtils.parseDate(bgtm), DateUtils.getEndTimeOfDay(edtm));
        this.appendOrderItem(page.getRecords(), orderItemWrapper);
        return page;
    }

    @Override
    public List<Order> listPageByTime(String lastCreateTime, Integer lastRowNum, Integer size, String userName) {
        //lastCreateTime 有助于快速定位当前查询的分表 ，如果是第一页则可不传，默认使用当前时间
        Date date = StringUtils.isBlank(lastCreateTime) ? new Date() : DateUtils.parseTime(lastCreateTime);
        String suffix = ShardingUtils.getSuffixByYearMonth(date);
        int resultSize = size == null ? 10 : size;
        //rowNum用于获取当前页数据的起始位置，如果是第一页可以不传，默认为0
        int rowNum = lastRowNum == null ? 0 : lastRowNum;
        List<Order> orderList = baseMapper.listByRowNum(suffix, resultSize, rowNum, userName);
        if (orderList.size() > 0) {
            while (orderList.size() < resultSize) { //查询出的数据不足 找更早的分表补足
                if ("2020_6".equals(suffix)) {    //假设最早的分表为 t_order_2020_6
                    break;
                }
                suffix = ShardingUtils.getPrevSuffix(suffix);
                List<Order> tempOrderList = baseMapper.listByRowNum(suffix, resultSize - orderList.size(), 0, userName);
                if (tempOrderList.size() > 0) {
                    orderList.addAll(tempOrderList);
                }
            }
            //获取orderList中数据的时间范围 查询子表数据
            Wrapper<OrderItem> orderItemWrapper = new EntityWrapper<OrderItem>()
                    .between("order_create_time", orderList.get(orderList.size() - 1).getCreateTime(), orderList.get(0).getCreateTime());
            this.appendOrderItem(orderList, orderItemWrapper);
        }
        return orderList;
    }

    @Override
    public Order findById(Long id, String createTime) {
        return selectOne(new EntityWrapper<Order>().eq("id", id).eq("create_time", DateUtils.parseTime(createTime)));
    }

    @Override
    public void removeById(Long id, String createTime) {
        delete(new EntityWrapper<Order>().eq("id", id).eq("create_time", DateUtils.parseTime(createTime)));
    }

    private void appendOrderItem(List<Order> orderList, Wrapper<OrderItem> ew) {
        if (null != orderList && orderList.size() > 0) {
            Set<Long> orderIds = orderList.stream().map(Order::getId).collect(Collectors.toSet());
            ew.in("order_id", orderIds).orderBy("order_create_time");
            List<OrderItem> orderItemList = orderItemService.selectList(ew);
            Map<Long, List<OrderItem>> orderItemMap = DataUtils.listToMapForLong("orderId", orderItemList);
            orderList.forEach(order -> order.setOrderItemList(orderItemMap.get(order.getId())));
        }
    }

    private void addBatch(int ct, int size, List<Order> tempList, List<OrderItem> orderItemList) {
        for (int i = 0; i < size; i++) {
            long orderId = IdWorker.getId();
            Date orderCreateTime = randomCreateTime(new Random());
            tempList.add(new Order().setId(orderId).setName("订单-" + (ct - i)).setCreateTime(orderCreateTime)
                    .setUserName("guest-" + new Random().nextInt(1000)));
            orderItemList.add(new OrderItem().setOrderId(orderId).setName("商品-" + (ct * 2 - i * 2)).setOrderCreateTime(orderCreateTime));
            orderItemList.add(new OrderItem().setOrderId(orderId).setName("商品-" + (ct * 2 - i * 2 - 1)).setOrderCreateTime(orderCreateTime));
        }
        insertBatch(tempList);
        orderItemService.insertBatch(orderItemList);
        tempList.clear();
        orderItemList.clear();
    }

    private Date randomCreateTime(Random random) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.DAY_OF_MONTH, random.nextInt(31) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, random.nextInt(24));
        calendar.set(Calendar.MINUTE, random.nextInt(60));
        calendar.set(Calendar.SECOND, random.nextInt(60));
        return calendar.getTime();
    }

}
