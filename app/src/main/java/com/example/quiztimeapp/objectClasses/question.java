package com.example.quiztimeapp.objectClasses;

public class question {
    String question_content;
    String question_type;
    String imageAttachment;
    String answer;
    int belongToQuizID;

    public question() {
    }

    public question(String question_content, String question_type, String answer) {
        this.question_content = question_content;
        this.question_type = question_type;
        this.imageAttachment = "";
        this.answer= answer;
        this.belongToQuizID = -1; //not yet saved to local db
    }

    public question(String question_content, String question_type, String imageAttachment, String answer, int belongToQuizID) {
        this.question_content = question_content;
        this.question_type = question_type;
        this.imageAttachment = imageAttachment;
        this.answer = answer;
        this.belongToQuizID = belongToQuizID;
    }

    public int getBelongToQuizID() {
        return belongToQuizID;
    }

    public void setBelongToQuizID(int belongToQuizID) {
        this.belongToQuizID = belongToQuizID;
    }

    public question(String question_content, String question_type, String question_correct_answer, String myAns) {
        this.question_content = question_content;
        this.question_type = question_type;
        this.imageAttachment = "";
        this.answer= "<2cm!>" + question_correct_answer +  "<2cm!>" + myAns; //  <2cm!> indicate 2 answer --> correct ans & my ans
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    @Override
    public String toString() {
        return "question{" +
                "question_content='" + question_content + '\'' +
                ", question_type='" + question_type + '\'' +
                ", imageAttachment='" + imageAttachment + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
