package com.example.admin_demo.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    /**
     * 读取指定文件路径的内容，并返回字符串
     *
     * @param filePath 文件路径
     * @return 文件内容
     */
    public static String readFile(String filePath) {
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append(System.lineSeparator());  // 保留换行符
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString().trim();  // 去除末尾的多余换行符
    }

    /**
     * 写入内容到指定的文件路径
     *
     * @param filePath 文件路径
     * @param content  要写入的内容
     */
    public static void writeFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}