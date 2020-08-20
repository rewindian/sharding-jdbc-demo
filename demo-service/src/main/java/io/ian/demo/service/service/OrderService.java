package io.ian.demo.service.service;

import com.baomidou.mybatisplus.plugins.Page;
import io.ian.demo.service.entity.Order;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author XHD
 * @since 2020-07-16
 */
public interface OrderService extends IService<Order> {

    void initData();

    Page<Order> listPage(String bgtm, String edtm, Integer current, Integer size, String userName);

    List<Order> listPageByTime(String lastCreateTime, Integer lastRowNum, Integer size, String userName);

    Order findById(Long id, String createTime);

    void removeById(Long id, String createTime);
}
