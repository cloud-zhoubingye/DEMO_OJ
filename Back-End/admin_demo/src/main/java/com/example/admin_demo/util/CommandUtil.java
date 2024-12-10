package com.example.admin_demo.util;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;

public class CommandUtil {
    /**
     * 运行外部命令并捕获输出和错误。
     *
     * @param cmd       要执行的命令
     * @param inputFilePath 输入文件路径
     * @param stdoutFile 标准输出文件路径
     * @param stderrFile 标准错误文件路径
     * @return 子进程的退出代码
     */
    public static int run(String cmd, String inputFilePath, String stdoutFile, String stderrFile) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(cmd.split(" "));
            // 重定向错误流到文件
            File errorFile = new File(stderrFile);
            processBuilder.redirectError(errorFile);
            // 重定向输出流到文件
            if (stdoutFile != null) {
                File outputfile = new File(stdoutFile);
                processBuilder.redirectOutput(outputfile);
            }
            // 设置输入文件
            File inputFile = new File(inputFilePath);
            if (!inputFile.exists()) {
                throw new FileNotFoundException("Input file not found: " + inputFilePath);
            }

            Process process = processBuilder.start();

            // 将输入文件的内容写入到process的输入流中
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                try (FileInputStream fis = new FileInputStream(inputFilePath);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(fis, Charset.forName("GBK")))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return 2; // 发生IO异常，视为运行时错误
            }
            process.getOutputStream().close(); // 关闭输入流

            // 等待子进程结束
            int exitCode = process.waitFor();
            System.out.println("Exit code: " + exitCode);
            return exitCode;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return 2; // 发生IO异常或中断异常，视为运行时错误
        }
    }
}