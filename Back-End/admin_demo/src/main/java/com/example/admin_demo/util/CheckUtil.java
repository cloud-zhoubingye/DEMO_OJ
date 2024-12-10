package com.example.admin_demo.util;

import java.io.*;
import java.util.*;

public class CheckUtil {
    /**
     * 比较输出文件和标准答案文件是否相同。
     *
     * @param outputFile 输出文件路径
     * @param answerFile 标准答案文件路径
     * @return 如果相同返回true，否则返回false
     */
    public static boolean compareFiles(String outputFile, String answerFile) {
        try (BufferedReader outputReader = new BufferedReader(new FileReader(outputFile));
             BufferedReader answerReader = new BufferedReader(new FileReader(answerFile))) {
            String outputLine;
            String answerLine;

            while ((outputLine = outputReader.readLine()) != null) {
                answerLine = answerReader.readLine();
                if (answerLine == null || !outputLine.equals(answerLine)) {
                    return false; // 文件内容不匹配
                }
            }
            return answerReader.readLine() == null; // 检查是否有额外的行
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 检查所有输出文件是否与标准答案文件匹配。
     *
     * @param stdoutFiles 输出文件路径列表
     * @param answerFiles 标准答案文件路径列表
     * @return 返回分数
     */
    public static int checkAllFiles(List<String> stdoutFiles, List<String> answerFiles) {
        float full = answerFiles.size();//测试样例数目
        float count = 0;
        for (int i = 0; i < stdoutFiles.size(); i++) {
            if (!compareFiles(stdoutFiles.get(i), answerFiles.get(i))) {
                count += 1;
            }
        }
        return (int)(count/full);
    }
}