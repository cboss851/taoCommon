package com.tao.commons.utils;

import java.util.List;

/**
 * mxl操作
 *
 * @Author：cboss
 */
public class XmlUtils {

    /**
     * 在mapper.xml的mapper添加方法
     *
     * @param fileName 类文件全路径
     * @param content  方法内容
     */
    public static void addMethod(String fileName, String content) {
        //1:按行读取文件保存在List
        List<String> lineList = FileUtils.readContent(fileName);

        //2:在类的最后面添加方法
        for (int i = lineList.size() - 1; i >= 0; i--) {
            String lineContent = lineList.get(i);
            if ("</mapper>".equalsIgnoreCase(lineContent.trim())) {
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
        FileUtils.writeText(fileName, newContent, false);
    }
}