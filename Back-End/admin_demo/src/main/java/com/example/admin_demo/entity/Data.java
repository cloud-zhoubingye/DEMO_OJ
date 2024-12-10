package com.example.admin_demo.entity;

public class Data {
    private String code;
    private String language;
    private String username;
    private Long prob;
    private int id;
    public Data(int id, String code, String language, Long prob, String username) {
        this.code = code;
        this.id = id;
        this.language = language;
        this.prob = prob;
        this.username = username;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public Long getProb() {
        return prob;
    }
    public void setProb(Long prob) {
        this.prob = prob;
    }
    public String getUsername(){return username;}
    public void setUsername(String username){
        this.username = username;
    }
}