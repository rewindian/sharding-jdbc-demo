

CREATE TABLE `t_order_2020_6` (
                                  `id` bigint(20) NOT NULL COMMENT '主键',
                                  `name` varchar(20) DEFAULT NULL COMMENT '名称',
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `user_name` varchar(20) DEFAULT NULL COMMENT '订单所属用户名',
                                  PRIMARY KEY (`id`),
                                  KEY `user_name_index` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';



CREATE TABLE `t_order_item_2020_6` (
                                       `id` bigint(20) NOT NULL COMMENT '主键',
                                       `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
                                       `name` varchar(20) DEFAULT NULL COMMENT '名称',
                                       `order_create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
                                       PRIMARY KEY (`id`),
                                       KEY `order_id_index` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品表';