package com.tao.commons.utils;

import java.util.List;

/**
 * 类操作，添加方法、变量
 *
 * @Author：cboss
 */
public class ClassUtils {

    /**
     * 在类的最后面添加方法
     *
     * @param classFileName 类文件全路径
     * @param content       方法内容
     */
    public static void addMethod(String classFileName, String content) {
        //1:按行读取文件保存在List
        List<String> lineList = FileUtils.readContent(classFileName);

        //2:在类的最后面添加方法
        for (int i = lineList.size() - 1; i >= 0; i--) {
            String lineContent = lineList.get(i);
            if ("}".equalsIgnoreCase(lineContent.trim())) {
                lineList.add(i, content);
                break;
            }
        }

        //3:将List转换为字符串并保存到文件中
        StringBuilder sb = new StringBuilder();
        for (String line : lineList) {
            sb.append(line).append("\n");
        }
        String newContent = sb.substring(0, sb.length() - 1);
        FileUtils.writeText(classFileName, newContent, false);
    }
}