package com.tao.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * ID生成工具类
 * @Author：cboss
 * @Date：2023/3/26
 */
public class IdUtils {
    private static final String DATE_FORMAT = "yyMMddHHmmssSSS";

    public static String UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 19位ID，格式：yyMMddHHmmssSSS+4位随机数
     * @return
     */
    public static String timeID() {
        Date now = new Date();
        String timestamp = new SimpleDateFormat(DATE_FORMAT).format(now);
        String randomNumber = RandomUtils.num(4);
        return timestamp + randomNumber;
    }

    public static void main(String[] args) {
        System.out.println("UUID:"+IdUtils.UUID());
        System.out.println("numID:"+IdUtils.timeID());
    }
}
