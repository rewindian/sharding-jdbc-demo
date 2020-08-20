package io.ian.demo.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author: Xu Haidong
 * @Date: 2019/4/11
 */
@Slf4j
public final class DataUtils {


    /**
     * 将List转为map , map的key是T中的prop属性的值
     *
     * @param prop
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Map<String, List<T>> listToMap(String prop, List<T> list) {
        if (StringUtils.isEmpty(prop)) {
            return new HashMap<>();
        }
        String getter = "get" + prop.substring(0, 1).toUpperCase() + prop.substring(1);
        Map<String, List<T>> resultMap = new HashMap<>();
        if (null != list && list.size() > 0) {
            list.forEach(item -> {
                String key = "";
                Class clazz = item.getClass();
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if (getter.equals(method.getName())) {
                        try {
                            key = method.invoke(item).toString();
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                            continue;
                        }
                        break;
                    }
                }

                if (resultMap.containsKey(key)) {
                    (resultMap.get(key)).add(item);
                } else {
                    List<T> tempList = new ArrayList<>();
                    tempList.add(item);
                    resultMap.put(key, tempList);
                }
            });
        }
        return resultMap;
    }

    public static <T> Map<Long, List<T>> listToMapForLong(String prop, List<T> list) {
        if (StringUtils.isEmpty(prop)) {
            return new HashMap<>();
        }
        String getter = "get" + prop.substring(0, 1).toUpperCase() + prop.substring(1);
        Map<Long, List<T>> resultMap = new HashMap<>();
        if (null != list && list.size() > 0) {
            list.forEach(item -> {
                Long key = null;
                Class clazz = item.getClass();
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if (getter.equals(method.getName())) {
                        try {
                            key = (Long) method.invoke(item);
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                            continue;
                        }
                        break;
                    }
                }

                if (resultMap.containsKey(key)) {
                    (resultMap.get(key)).add(item);
                } else {
                    List<T> tempList = new ArrayList<>();
                    tempList.add(item);
                    resultMap.put(key, tempList);
                }
            });
        }
        return resultMap;
    }

    /**
     * 实体类转Map
     *
     * @param object
     * @return
     */
    public static Map<String, Object> entityToMap(Object object) {
        Map<String, Object> map = new HashMap();
        for (Field field : object.getClass().getDeclaredFields()) {
            if ("serialVersionUID".equals(field.getName())) {
                continue;
            }
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(object);
                map.put(field.getName(), o);
                field.setAccessible(flag);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return map;
    }

    /**
     * Map转实体类
     *
     * @param map    需要初始化的数据，key字段必须与实体类的成员名字一样，否则赋值为空
     * @param entity 需要转化成的实体类
     * @return
     */
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entity) {
        T t = null;
        try {
            t = entity.newInstance();
            for (Field field : entity.getDeclaredFields()) {
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object != null && field.getType().isAssignableFrom(object.getClass())) {
                        field.set(t, object);
                    }
                    field.setAccessible(flag);
                }
            }
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
        }
        return t;
    }

    public static boolean collectionIsEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean collectionIsNotEmpty(Collection collection) {
        return !collectionIsEmpty(collection);
    }

}
