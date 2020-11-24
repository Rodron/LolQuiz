package com.example.lolquiz;

public class Userscore {
    private String username;
    private String score;
    private String questions;

    public Userscore(String username, String score, String questions) {
        this.username = username;
        this.score = score;
        this.questions = questions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }
}
