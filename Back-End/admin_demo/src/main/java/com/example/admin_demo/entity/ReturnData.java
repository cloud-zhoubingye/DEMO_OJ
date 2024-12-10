package com.example.admin_demo.entity;

import java.util.List;

public class ReturnData {
    private int resultScore;
    private List<Result> resultData;
    public int getResultScore() {
        return resultScore;
    }
    public void setResultScore(int resultScore) {
        this.resultScore = resultScore;
    }
    public List<Result> getResultData() {
        return resultData;
    }
    public void setResultData(List<Result> resultData) {
        this.resultData = resultData;
    }
}
