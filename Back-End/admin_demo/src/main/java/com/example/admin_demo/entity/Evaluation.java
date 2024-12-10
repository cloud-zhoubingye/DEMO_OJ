package com.example.admin_demo.entity;

import java.time.LocalDateTime;

public class Evaluation {
    private int id;
    private String username;
    private LocalDateTime date;
    private String question;
    private int score;

    public Evaluation(int id,String username,LocalDateTime date,String question,int score) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.question = question;
        this.score = score;
    }

    public  Evaluation() {

    }
    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", date=" + date +
                ", question='" + question + '\'' +
                ", score=" + score +
                '}';
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
