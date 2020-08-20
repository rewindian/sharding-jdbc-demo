package io.ian.demo.service.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author XHD
 * @since 2020-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_order_item")
public class OrderItem extends Model<OrderItem> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 名称
     */
    private String name;
    /**
     * 订单创建时间
     */
    private Date orderCreateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
