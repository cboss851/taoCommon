package com.tao.commons.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 * @Author：cboss
 */
public class FileUtils {
    //判断文件是否存在
    public static boolean isExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    //判断是否是文件夹
    public static boolean isDir(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.isDirectory();
        } else {
            return false;
        }
    }

    /**
     * 路径+文件名创建文件
     *
     * @param fullPathName
     * @return
     */
    public static File createNewFile(String fullPathName) throws IOException {
        File file = new File(fullPathName);
        if (!file.exists()) {
            File directory = file.getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }
            file.createNewFile();
        }
        return file;
    }

    //删除文件
    public static boolean deleteFile(String pathname) {
        boolean result = false;
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
            result = true;
        }
        return result;
    }

    /**
     * 获取文件内容
     *
     * @param path
     */
    public static List<String> readContent(String path) {
        List<String> contentList = new ArrayList<String>();
        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
             BufferedReader br = new BufferedReader(isr)) {
            String line = "";
            while ((line = br.readLine()) != null) {
                contentList.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contentList;
    }

    //读取Text文件操作
    public static String readText(String filePath) {
        String lines = "";
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                lines += line + "\n";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

    //写入Text文件操作
    public static void writeText(String filePath, String content, boolean isAppend) {
        FileOutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = new FileOutputStream(filePath, isAppend);
            outputStreamWriter = new OutputStreamWriter(outputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
