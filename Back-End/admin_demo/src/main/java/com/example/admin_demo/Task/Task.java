package com.example.admin_demo.Task;

import com.example.admin_demo.entity.*;
import com.example.admin_demo.util.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.io.*;
import java.nio.file.*;
@Service
public class Task {
    /**
     * 工作目录
     * CURRENT_DIR表示temp文件夹下
     */
    private static String CURRENT_DIR = System.getProperty("user.dir")+"/temp/";
    private static String WORK_DIR ;
    /**
     * 类文件
     */
    private static String CLASS;
    /**
     * 代码
     */
    private static String CODE = null;
    /**
     * 编译异常存放路径
     */
    private static String COMPILE_ERROR=null;
    /**
     * 正常输出的路径
     */
    private static String STDOUT=null;
    /**
     * 异常输出的路径
     */
    private static String STDERR=null;
    /**
     * 工作目录
     */
    private static File workDir;
    //文件
    private static String INPUT_FILE = null;
    //private static String OUTPUT_FILE = null;
    public Task(){
        /**
         * 每一份代码提交，都会创建一个新的目录：./temp/ +UUID.randomUUID/相应文件
         * 这样避免了不同的提交之间的相互干扰
         */
        WORK_DIR = CURRENT_DIR+UUID.randomUUID()+"/";
        COMPILE_ERROR = WORK_DIR + "compileError.txt";
        STDOUT = WORK_DIR + "stdout.txt";
        STDERR = WORK_DIR + "stderr.txt";
        // INPUT_FILE = System.getProperty("user.dir") + "/testfile/input.in";
    }
    /**
     * 提供的核心方法就是 compileAndRun:含义就是编译和运行
     * 要编译运行的java代码@param question
     * option：1:C/C++，2:Python，3:JAVA
     * 编译运行的结果@return
     */
    public Answer compileAndRun(Submission submission, OjProblem problem, int option, String testInput){
        Answer answer = new Answer();
        answer.setexitCode(5);
        //获取题目的时间限制
        String timeLimitStr = problem.getTimeLimit();
        timeLimitStr = timeLimitStr.replaceAll("[^\\d]", ""); // 去掉所有非数字字符
        Long timeLimit = Long.parseLong(timeLimitStr); // 转换为long类型
        //第一步，准备好用来存放临时文件的目录
        workDir=new File(WORK_DIR);
        System.out.println("绝对路径是:"+workDir.getAbsolutePath());
        if(!workDir.exists()){
            //创建多级目录
            workDir.mkdirs();
        }
        INPUT_FILE = WORK_DIR + "input.in";
        File file = new File(INPUT_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将输入写入文件
        FileUtil.writeFile(INPUT_FILE, testInput);
        if(option == 1){//C/C++代码
            String sourceFile = "Solution.cpp";
            CODE = WORK_DIR + sourceFile;
            FileUtil.writeFile(CODE, submission.getCode());

            // 编译命令
            String compileCmd = String.format("g++ -o %s %s", WORK_DIR + "Solution", CODE);
            String runCmd = String.format("%s/Solution", WORK_DIR); // 确保输出文件路径正确

            // 运行编译命令
            CommandUtil.run(compileCmd, INPUT_FILE, null, COMPILE_ERROR);
            System.out.println("......");
            // 检查编译错误
            String compileOutput = FileUtil.readFile(COMPILE_ERROR);
            if (!compileOutput.isEmpty()) {
                answer.setexitCode(1);
                answer.setReason(compileOutput);
                return answer;
            }

            // 运行程序
            /**
             * 运行时间检测
             */
            long start = System.currentTimeMillis();
            Thread thread = new Thread(() -> CommandUtil.run(runCmd, INPUT_FILE, STDOUT, STDERR));
            thread.start();
            try {
                thread.join(timeLimit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            //说明超时了
            if (end - start >= timeLimit) {
                answer.setexitCode(4);
                return answer;
            }

            // 检查运行错误
            String runError = FileUtil.readFile(STDERR);
            if (!runError.isEmpty()) {
                answer.setexitCode(2);
                answer.setReason(runError);
                return answer;
            }

            // 正常运行
            answer.setexitCode(0);
            answer.setTime(end - start);
            answer.setStdout(FileUtil.readFile(STDOUT));
        }
        else if(option == 2){//Python代码
            String sourceFile = "Solution.py";
            CODE = WORK_DIR + sourceFile;
            FileUtil.writeFile(CODE, submission.getCode());
            // Python 代码不需要编译，直接运行
            String pythonCmd = "python3"; // "python3"
            // String runCmd = String.format("%s %s < %s", pythonCmd, CODE, INPUT_FILE);
            String runCmd = String.format("%s %s < %s", pythonCmd, CODE, INPUT_FILE);
            // 运行Python脚本
            /**
             * 运行时间检测
             */
            long start = System.currentTimeMillis();
            Thread thread = new Thread(() -> CommandUtil.run(runCmd, INPUT_FILE, STDOUT, STDERR));
            thread.start();
            try {
                thread.join(timeLimit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            //说明超时了
            if (end - start >= timeLimit) {
                answer.setexitCode(4);
                return answer;
            }

            // 检查运行错误
            String runError = FileUtil.readFile(STDERR);
            if (!runError.isEmpty()) {
                answer.setexitCode(2);
                answer.setReason(runError);
                return answer;
            }

            // 正常运行，获取输出
            answer.setexitCode(0);
            answer.setTime(end - start);
            answer.setStdout(FileUtil.readFile(STDOUT));
        }
        else if(option == 3) {//JAVA代码
            String sourceFile = "Solution.java";
            CODE = WORK_DIR+sourceFile;
            FileUtil.writeFile(CODE, submission.getCode());
            // 创建子进程,调用javac进行编译，并且指定路径
            /*javac -encoding gbk %s -d %s
             * 第一个%s表示代码文件位置
             * 先构造编译命令:-d，指定放置的生成类文件的位置，即第二个%s
             */
            String compileCmd = String.format("javac -encoding gbk %s -d %s ", CODE, WORK_DIR);
            System.out.println("编译命令:" + compileCmd);

            CommandUtil.run(compileCmd, INPUT_FILE, null, COMPILE_ERROR);
            //如果编译出错，javac就会把错误写入到stderr当中,用一个专门的文件来保存：compileError.txt
            String compileError = FileUtil.readFile(COMPILE_ERROR);

            System.out.println("......");
            //如果出现编译出错的情况
            if (!"".equals(compileError)) {
                //首先需要设置错误信息：直接返回ERROR
                System.out.println("编译出错");
                answer.setexitCode(1);
                answer.setReason(compileError);
                return answer;
            }
            //编译没有出错，得到.class文件
            //第四步:调用java命令并且执行代码
            //运行程序的时候，也会把java子进程的标准输入和标准输出获取到:stdout.txt,stderr.txt
            CLASS="Solution";
            String runCmd = String.format("java -classpath %s %s", WORK_DIR, CLASS);
            //这里INPUT_FILE先指定一个文件，后面的STDERR也指定一个输出文件，后面样例多了再使用循环
            /**
             * 运行时间检测
             */
            long start = System.currentTimeMillis();
            Thread thread = new Thread(() -> CommandUtil.run(runCmd, INPUT_FILE, STDOUT, STDERR));
            thread.start();
            try {
                thread.join(timeLimit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            //说明超时了
            if (end - start >= timeLimit) {
                answer.setexitCode(4);
                return answer;
            }
            //检验是否运行出错（运行异常）
            String runError = FileUtil.readFile(STDERR);
            //运行出错的情况
            if (!"".equals(runError)) {
                // System.out.println("运行异常");
                answer.setexitCode(2);
                answer.setReason(runError);
                return answer;
            }
            //正常运行
            answer.setexitCode(0);
            answer.setTime(end-start);
            answer.setStdout(FileUtil.readFile(STDOUT));
            return answer;
        }
        return answer;// 出错情况
    }
    //删除文件
    private static void deleteFile(File file) {
        if (file.exists() && !file.isDirectory()) {
            file.delete();
        }
    }
    //删除目录
    private static void deleteDirectory(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteFile(file);
                }
            }
            directory.delete();
        }
    }
    public static String checkAnswer(Answer answer, String testOutput) {
        switch (answer.getexitCode()) {
            case 5:
                System.out.println("Have not choose Language\n");
                // 删除工作目录
                deleteDirectory(workDir);
                return "Have not choose Language";
            //break;
            case 1:
                System.out.println("Compilation Error:\n" + answer.getReason());
                deleteDirectory(workDir);
                return "Compilation Error:\n" + answer.getReason();
            //break;
            case 2:
                System.out.println("Runtime Error\n");
                deleteDirectory(workDir);
                return "Runtime Error";
            //break;
            case 4:
                System.out.println("Time Limit Exceeded (TLE)");
                deleteDirectory(workDir);
                return "Time Limit Exceeded (TLE)";
            //break;
            case 0:
                try {
                    String actualOutput = answer.getStdout();
                    String expectedOutput = testOutput;
                    deleteDirectory(workDir);
                    return (actualOutput.equals(expectedOutput) ? "AC" : "WA");
                    //System.out.println(actualOutput.equals(expectedOutput) ? "AC" : "WA");
                } catch (Exception e) {
                    System.out.println("Error reading the Output file: " + e.getMessage());
                    deleteDirectory(workDir);
                    return "Error reading the Output file";
                }
                //break;
            default:
                System.out.println("Unknown exit code: " + answer.getexitCode());
                deleteDirectory(workDir);
                return "Unknown exit code";
        }
    }
    //    public static void main(String[] args) {
//        Task task = new Task();
//        //创建提交类，包含代码
//        Submission submission = new Submission();
//        //指定输入的代码文件，后面通过前端接口读取用户所写代码
//        String codePath = System.getProperty("user.dir") + "/testfile/Solution.cpp";
//        System.out.println(codePath);
//        //将文件内的代码写入提交类中
//        try {
//            String codeContent = new String(Files.readAllBytes(Paths.get(codePath)));
//            submission.setCode(codeContent);
//        } catch (IOException e) {
//            System.out.println("Error reading the Code file: " + e.getMessage());
//            return;
//        }
//        //创建问题类
//        Problem problem = new Problem();
//        //设置问题的运行时长限制，后面通过数据库接口读取题目数据
//        problem.setSeconds(5000);
//        //编译运行代码，并获取返回值
//        Answer answer = task.compileAndRun(submission, problem,1);
//        checkAnswer(answer);
//    }
    public static void main(String[] args) {
        String codePath = System.getProperty("user.dir") + "/testfile/Solution.py";
        try {
            // 读取文件的所有行
            List<String> lines = Files.readAllLines(Paths.get(codePath));
            // 将所有行连接成一个字符串，每行之间用系统默认的换行符分隔
            String content = String.join(System.lineSeparator(), lines);
            System.out.println(content);
            AiUtil aiUtil = new AiUtil();
            String status = aiUtil.aiCaller(content);
            System.out.println(status);
            if(status.equals("\"False\"")) System.out.println(1);
            else System.out.println(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//    public static void main(String[] args) {
//        Task task=new Task();
//        Submission submission =new Submission();
//        submission.setCode("\nclass Solution { public int twoSum(long[] nums, long target) {\n" +
//                "\t\tif(nums.length==2){if(nums[0]+nums[1]==target) return 1; else return 0;} " +
//                "\n\t\treturn 0;}" +
//                "\npublic static void main(String[] args){\n" +
//                "        Solution slo=new Solution();\n" +
//                "        //\n" +
//                "        int result1=slo.twoSum(new long[]{5214871,7123124},12337995);\n" +
//                "        if(result1==1){\n" +
//                "            System.out.println(\"Test OK\");\n" +
//                "        }else{\n" +
//                "            System.out.println(\"Test Error\");\n" +
//                "        }\n" +
//                "        //\n" +
//                "        int result2=slo.twoSum(new long[]{2,4},6);\n" +
//                "        if(result2==1){\n" +
//                "            System..println(\"Test OK\");\n" +
//                "        }else{\n" +
//                "            System.out.println(\"Test Error\");\n" +
//                "        }\n" +
//                "\n" +
//                "    }\n" +
//                "}");
//        Problem problem=new Problem();
//        problem.setSeconds(4000);
//        Answer answer=task.compileAndRun(submission, problem);
//        System.out.println("测试类当中的Code:"+ submission.getCode());
//        System.out.println(answer.getexitCode());
//        System.out.println(answer.getStderr());//null表示无错误
//        System.out.println(answer.getStdout());
//    }
