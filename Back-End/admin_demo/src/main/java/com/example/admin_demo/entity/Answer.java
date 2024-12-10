package com.example.admin_demo.entity;


/*
 * 这个类表示用户提交代码后，输出的结果
 * */
public class Answer {
    /**
     * 错误码的约定：
     * 0为编译运行都通过;
     * 1为编译出错;
     * 2表示运行出错（抛出异常）。
     */
    private int exitCode;

    private long time;
    /**
     * 存放各种异常出现原因
     */
    private String Reason;
    /**
     * 运行程序得到的标准输出的结果
     */
    private String stdOutput;
    /**
     * 运行程序得到的标准错误的结果
     */
    private String stderr;
    public int getexitCode() {
        return exitCode;
    }

    public void setexitCode(int code) {
        this.exitCode = code;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        this.Reason = reason;
    }

    public String getStdout() {
        return stdOutput;
    }

    public void setStdout(String stdout) {
        this.stdOutput = stdout;
    }

    public String getStderr() {
        return stderr;
    }

    public void setStderr(String stderr) {
        this.stderr = stderr;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
}
