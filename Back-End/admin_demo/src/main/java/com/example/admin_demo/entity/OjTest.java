package com.example.admin_demo.entity;

public class OjTest {

    private Long testId;        // 测试用例 ID，自增主键
    private Long problemId;     // 所属题目 ID
    private String testInput;   // 测试输入
    private String testOutput;  // 测试输出

    // Getters 和 Setters
    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getTestInput() {
        return testInput;
    }

    public void setTestInput(String testInput) {
        this.testInput = testInput;
    }

    public String getTestOutput() {
        return testOutput;
    }

    public void setTestOutput(String testOutput) {
        this.testOutput = testOutput;
    }

    @Override
    public String toString() {
        return "OjTest{" +
                "testId=" + testId +
                ", problemId=" + problemId +
                ", testInput='" + testInput + '\'' +
                ", testOutput='" + testOutput + '\'' +
                '}';
    }
}
