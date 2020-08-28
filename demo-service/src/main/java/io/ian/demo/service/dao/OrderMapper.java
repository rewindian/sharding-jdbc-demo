package io.ian.demo.service.dao;

import io.ian.demo.service.entity.Order;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author XHD
 * @since 2020-07-16
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<Order> listByRowNum(@Param("suffix") String suffix, @Param("size") Integer size, @Param("rowNum") Integer rowNum
            , @Param("userName") String userName);

}
