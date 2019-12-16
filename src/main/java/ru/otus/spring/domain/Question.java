package ru.otus.spring.domain;

import java.util.List;

public class Question {

    private String number;
    private String question;
    private List<String> variants;
    private List<String> rightAnswers;

    public Question(String number, String question, List<String> variants, List<String> rightAnswers) {
        this.number = number;
        this.question = question;
        this.variants = variants;
        this.rightAnswers = rightAnswers;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getVariants() {
        return variants;
    }

    public void setVariants(List<String> variants) {
        this.variants = variants;
    }

    public List<String> getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(List<String> rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public String toString() {
        return "number: " + this.number + "; question = " + this.question +
                "; variants = " + this.variants + "; rightAnswers = " + rightAnswers;
    }

    public boolean checkAnswer(String answer) {
        return rightAnswers.contains(answer);
    }
}
