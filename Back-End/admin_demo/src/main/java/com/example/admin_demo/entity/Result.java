package com.example.admin_demo.entity;

public class Result {
    private long id;
    private long memoryCost;
    private long timeCost;
    private int resultScore;
    private String resultInformation;
    public Result(long id, long memoryCost, long timeCost, int resultScore, String resultInformation) {
        this.id = id;
        this.memoryCost = memoryCost;
        this.timeCost = timeCost;
        this.resultScore = resultScore;
        this.resultInformation = resultInformation;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getMemoryCost() {
        return memoryCost;
    }
    public void setMemoryCost(long memoryCost) {
        this.memoryCost = memoryCost;
    }
    public long getTimeCost() {
        return timeCost;
    }
    public void setTimeCost(long timeCost) {
        this.timeCost = timeCost;
    }
    public int getResultScore() {
        return resultScore;
    }
    public void setResultScore(int resultScore) {
        this.resultScore = resultScore;
    }
    public String getResultInformation() {
        return resultInformation;
    }
    public void setResultInformation(String resultInformation) {
        this.resultInformation = resultInformation;
    }
}
