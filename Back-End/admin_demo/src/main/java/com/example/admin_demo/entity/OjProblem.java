package com.example.admin_demo.entity;

/**
 * OjProblem 实体类，用于表示题目信息
 */
public class OjProblem {

    private Long problemId;       // 题目 ID，自增主键
    private String title;         // 题目标题
    private String timeLimit;     // 时间限制
    private String memoryLimit;   // 内存限制
    private String description;   // 题目描述
    private String input;         // 输入描述
    private String output;        // 输出描述
    private String level;         // 题目难度
    private String problemCategory; // 题目分类

    // 构造方法
    public OjProblem() {}

    public OjProblem(Long problemId, String title, String timeLimit, String memoryLimit, String description,
                     String input, String output, String level, String problemCategory) {
        this.problemId = problemId;
        this.title = title;
        this.timeLimit = timeLimit;
        this.memoryLimit = memoryLimit;
        this.description = description;
        this.input = input;
        this.output = output;
        this.level = level;
        this.problemCategory = problemCategory;
    }

    // Getters and Setters
    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(String memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProblemCategory() {
        return problemCategory;
    }

    public void setProblemCategory(String problemCategory) {
        this.problemCategory = problemCategory;
    }

    @Override
    public String toString() {
        return "OjProblem{" +
                "problemId=" + problemId +
                ", title='" + title + '\'' +
                ", timeLimit='" + timeLimit + '\'' +
                ", memoryLimit='" + memoryLimit + '\'' +
                ", description='" + description + '\'' +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", level='" + level + '\'' +
                ", problemCategory='" + problemCategory + '\'' +
                '}';
    }
}
