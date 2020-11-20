package com.example.quiztimeapp.objectClasses;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Quiz implements Serializable {
    private String title;
    private String description;
    private String attachment;
    private String dateCreated;
    private int totalQuestions;
    private int quizID;


    public Quiz(String title, String description, String attachment, String dateCreated, int totalQuestions, int quizId) {
        this.title = title;
        this.description = description;
        this.attachment = attachment;
        this.dateCreated = dateCreated;
        this.totalQuestions = totalQuestions;
        this.quizID=quizId;
    }

    public Quiz(String title, String description, String attachment, int totalQuestions) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        this.title = title;
        this.description = description;
        this.attachment = attachment;
        this.totalQuestions = totalQuestions;
        this.dateCreated = formatter.format(date);
        this.quizID = -1; // -1 for non-saved quiz
    }

    public Quiz() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        this.dateCreated = formatter.format(date);
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", attachment='" + attachment + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", totalQuestions=" + totalQuestions +
                ", quizID=" + quizID +
                '}';
    }
}
