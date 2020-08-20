package io.ian.demo.web.handler;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler extends MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object fieldType = getFieldValByName("createTime", metaObject);//mybatis-plus版本2.0.9+
        if (fieldType == null) {
            setFieldValByName("createTime", new Date(), metaObject);//mybatis-plus版本2.0.9+
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object fieldType = getFieldValByName("updateTime", metaObject);//mybatis-plus版本2.0.9+
        if (fieldType == null) {
            setFieldValByName("updateTime", new Date(), metaObject);//mybatis-plus版本2.0.9+
        }
    }
}
