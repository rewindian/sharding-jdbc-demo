package io.ian.demo.service.service.impl;

import io.ian.demo.service.entity.OrderItem;
import io.ian.demo.service.dao.OrderItemMapper;
import io.ian.demo.service.service.OrderItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author XHD
 * @since 2020-07-16
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

}
