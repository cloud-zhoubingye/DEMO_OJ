package com.example.admin_demo.entity;

public class OjResource {

    private Long resourceId;           // 资源 ID，自增主键
    private Long problemId;            // 所属题目 ID
    private String resource;           // 资源内容（可以是路径或文件内容）
    private String resourceDescription; // 资源描述

    // Getters 和 Setters
    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    @Override
    public String toString() {
        return "OjResource{" +
                "resourceId=" + resourceId +
                ", problemId=" + problemId +
                ", resource='" + resource + '\'' +
                ", resourceDescription='" + resourceDescription + '\'' +
                '}';
    }
}
