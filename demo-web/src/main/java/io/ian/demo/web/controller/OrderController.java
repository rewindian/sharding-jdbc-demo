package io.ian.demo.web.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.ian.demo.core.model.RestResult;
import io.ian.demo.core.util.DateUtils;
import io.ian.demo.service.entity.Order;
import io.ian.demo.service.service.OrderItemService;
import io.ian.demo.service.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author XHD
 * @since 2020-07-16
 */
@Api(tags = "订单")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderItemService orderItemService;

    @ApiOperation(value = "根据时间段查询列表数据", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bgtm", value = "开始时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "edtm", value = "结束时间", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping("/listAllByDate")
    public ResponseEntity<RestResult> listAll(@RequestParam String bgtm, @RequestParam String edtm) {
        return ResponseEntity.ok(RestResult.getSuccessRestResult(orderService.selectList(new EntityWrapper<Order>()
                .between("create_time", DateUtils.parseDate(bgtm), DateUtils.getEndTimeOfDay(edtm))
                .orderBy("create_time", false))));
    }

    @ApiOperation(value = "时间段分页查询", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bgtm", value = "开始时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "edtm", value = "结束时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "current", value = "当前页", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query")
    })
    @GetMapping("/listPage")
    public ResponseEntity<RestResult> listPage(@RequestParam String bgtm, @RequestParam String edtm, @RequestParam Integer current,
                                               Integer size, String userName) {
        return ResponseEntity.ok(RestResult.getSuccessRestResult(orderService.listPage(bgtm, edtm, current, size, userName)));
    }

    /**
     * 从第二页开始lastCreateTime和lastRowNum为必选参数
     */
    @ApiOperation(value = "无限滚动分页查询", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lastCreateTime", value = "上一页最后一条数据的创建时间", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "lastRowNum", value = "上一页最后一条数据的行号", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页条数", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", paramType = "query")
    })
    @GetMapping("/listPageByTime")
    public ResponseEntity<RestResult> listPageByTime(String lastCreateTime, Integer lastRowNum, Integer size, String userName) {
        return ResponseEntity.ok(RestResult.getSuccessRestResult(orderService.listPageByTime(lastCreateTime, lastRowNum, size, userName)));
    }

    @ApiOperation(value = "保存信息", httpMethod = "POST")
    @PostMapping("/save")
    public ResponseEntity<RestResult> save(@RequestBody Order order) {
        orderService.insert(order);
        if (null != order.getOrderItemList() && order.getOrderItemList().size() > 0) {
            order.getOrderItemList().forEach(orderItem -> orderItem.setOrderId(order.getId()).setOrderCreateTime(order.getCreateTime()));
            orderItemService.insertBatch(order.getOrderItemList());
        }
        return ResponseEntity.ok(RestResult.getSuccessRestResult());
    }

    /**
     * 制造测试数据
     *
     * @return
     */
    @ApiOperation(value = "制造测试数据", httpMethod = "POST")
    @PostMapping("/initData")
    public ResponseEntity<RestResult> initData() {
        orderService.initData();
        return ResponseEntity.ok(RestResult.getSuccessRestResult());
    }

    /**
     * createTime 字段有助于快速定位该数据所在分表
     */
    @ApiOperation(value = "根据主键查询", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/findById")
    public ResponseEntity<RestResult> findById(@RequestParam Long id, @RequestParam String createTime) {
        return ResponseEntity.ok(RestResult.getSuccessRestResult(orderService.findById(id, createTime)));
    }

    /**
     * createTime 字段有助于快速定位该数据所在分表
     */
    @ApiOperation(value = "根据主键刪除", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "createTime", value = "创建时间", required = true, dataType = "String", paramType = "query"),
    })
    @GetMapping("/removeById")
    public ResponseEntity<RestResult> removeById(@RequestParam Long id, @RequestParam String createTime) {
        orderService.removeById(id, createTime);
        return ResponseEntity.ok(RestResult.getSuccessRestResult());
    }
}

