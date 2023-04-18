package com.tao.commons.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtils {

    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen = length(cs);
        if (strLen == 0) {
            return true;
        } else {
            for (int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 获取大括号{}里面的内容
     * msg = "account:create:{assetsType}_{accType}_{userId}_{userType}"
     * list = assetsType,accType,userId,userType的集合
     *
     * @param msg
     * @return
     */
    public static List<String> getBraceContent(String msg) {
        List<String> list = new ArrayList<>();
        Pattern p = Pattern.compile("(\\{[^\\}]*\\})");
        Matcher m = p.matcher(msg);
        while (m.find()) {
            list.add(m.group().substring(1, m.group().length() - 1));
        }
        return list;
    }

    /**
     * 字符串中首字母转换成大写
     *
     * @param str
     * @return
     */
    public static String upperFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * 字符串中首字母转换成大写，其它全部转换成小写
     *
     * @param str
     * @return
     */
    public static String upperFirstLowerOther(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            chars[i] = Character.toLowerCase(chars[i]);
        }
        return new String(chars);
    }


    /**
     * 字符串中首字母转换成大写
     *
     * @param str
     * @return
     */
    public static String lowerFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    /**
     * 字符串中首字母转换成大写，其它全部转换成小写
     *
     * @param str
     * @return
     */
    public static String lowerFirstOtherUpper(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            chars[i] = Character.toUpperCase(chars[i]);
        }
        return new String(chars);
    }

    /**
     * 类名使用 UpperCamelCase 风格
     * abc     --> Abc
     * abc_def --> AbcDef
     * ABC_DEF --> AbcDef
     */
    public static String upperCamelCase(String req) {
        if (isBlank(req)) return "";
        StringBuilder rsp = new StringBuilder();
        for (String name : req.split("_")) {
            rsp.append(upperFirstLowerOther(name));
        }
        return rsp.toString();
    }

    /**
     * 方法名、参数名、成员变量、局部变量都统一使用 lowerCamelCase 风格
     * abc     --> abc
     * abc_def --> abcDef
     * ABC_DEF --> abcDef
     */
    public static String lowerCamelCase(String req) {
        if (isBlank(req)) return "";
        StringBuilder rsp = new StringBuilder();
        for (String name : req.split("_")) {
            if (rsp.length() == 0) {
                rsp.append(name.toLowerCase());
            } else {
                rsp.append(upperFirstLowerOther(name));
            }
        }
        return rsp.toString();
    }
}
