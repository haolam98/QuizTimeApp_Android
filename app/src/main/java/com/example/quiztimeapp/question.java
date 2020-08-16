package com.example.quiztimeapp;

public class question {
    String question_content;
    String question_type;
    String question_correct_answer;
    String imageAttachment;

    public question() {
    }

    public question(String question_content, String question_type, String question_correct_answer) {
        this.question_content = question_content;
        this.question_type = question_type;
        this.question_correct_answer = question_correct_answer;
        this.imageAttachment = null;
    }

    public question(String question_content, String question_type, String question_correct_answer, String imageAttachment) {
        this.question_content = question_content;
        this.question_type = question_type;
        this.question_correct_answer = question_correct_answer;
        this.imageAttachment = imageAttachment;
    }

    public String getImageAttachment() {
        return imageAttachment;
    }

    public void setImageAttachment(String imageAttachment) {
        this.imageAttachment = imageAttachment;
    }

    public String getQuestion_content() {
        return question_content;
    }

    public void setQuestion_content(String question_content) {
        this.question_content = question_content;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public String getQuestion_correct_answer() {
        return question_correct_answer;
    }

    public void setQuestion_correct_answer(String question_correct_answer) {
        this.question_correct_answer = question_correct_answer;
    }
}
