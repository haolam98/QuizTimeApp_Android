package com.example.quiztimeapp;

import java.util.ArrayList;

public class quiz_package {
    String title;
    String date;
    String duration;
    int totalQuestions;
    ArrayList<question> questions;

    public quiz_package() {
    }

    public quiz_package(String title, String date, String duration, int totalQuestions, ArrayList<question> questions) {
        this.title = title;
        this.date = date;
        this.duration = duration;
        this.totalQuestions = totalQuestions;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public ArrayList<question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<question> questions) {
        this.questions = questions;
    }
}
