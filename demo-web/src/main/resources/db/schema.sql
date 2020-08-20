DROP TABLE IF EXISTS t_order;

CREATE TABLE `t_order`
(
    `id`   bigint(20) NOT NULL COMMENT '主键',
    `name` varchar(20) DEFAULT NULL COMMENT '名称',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_order_item;

CREATE TABLE `t_order_item`
(
    `id`       bigint(20) NOT NULL COMMENT '主键',
    `order_id` bigint(20)  DEFAULT NULL COMMENT '订单ID',
    `name`     varchar(20) DEFAULT NULL COMMENT '名称',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;