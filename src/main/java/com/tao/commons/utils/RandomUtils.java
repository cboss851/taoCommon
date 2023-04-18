package com.tao.commons.utils;

import java.util.Random;

/**
 * 随机数生成工具类
 * @Author：cboss
 * @Date：2023/3/26
 */
public class RandomUtils {
    private static final String RANDOM_NUM_STRING = "0123456789";

    public static String num(int len) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int index = random.nextInt(RANDOM_NUM_STRING.length());
            builder.append(RANDOM_NUM_STRING.charAt(index));
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.printf("："+ RandomUtils.num(4));
    }
}
