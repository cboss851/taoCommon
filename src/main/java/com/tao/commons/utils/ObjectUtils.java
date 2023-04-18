package com.tao.commons.utils;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @Author：cboss
 */
public class ObjectUtils {

    /**
     * 根据属性名获取属性值
     */
    public static Object getFieldValueByName(Object object, String fieldName) throws Exception {
        if (object instanceof Number || object instanceof String) return object;
        String methodNameSuffix = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        Method method = object.getClass().getMethod("get" + methodNameSuffix, new Class[]{});
        return method.invoke(object, new Object[]{});
    }

    /**
     * POJO转换为Map
     *
     * @param pojo
     * @return
     */
    public static HashMap<String, Object> convertPojoToHashMap(Object pojo) throws IllegalAccessException {
        HashMap<String, Object> map = new HashMap<>();
        Field[] fields = pojo.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(pojo));
        }
        return map;
    }

    /**
     * 深度克隆
     *
     * @param obj
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> T deepClone(Object obj, Class<T> clazz) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);

        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return clazz.cast(oi.readObject());
    }
}
