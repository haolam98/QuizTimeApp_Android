package com.example.quiztimeapp.objectClasses;

public class Answer {
    String asnwer_content;
    Boolean isCorrect;

    public Answer(String asnwer_content, Boolean isCorrect) {
        this.asnwer_content = asnwer_content;
        this.isCorrect = isCorrect;
    }

    public Answer(String asnwer_content) {
        this.asnwer_content = asnwer_content;
    }

    public Answer() {
    }

    public String getAsnwer_content() {
        return asnwer_content;
    }

    public void setAsnwer_content(String asnwer_content) {
        this.asnwer_content = asnwer_content;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
