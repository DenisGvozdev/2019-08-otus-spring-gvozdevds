package ru.otus.spring.domain;

public class Answer {

    private String answer;
    private boolean right;

    String getAnswer() {
        return answer;
    }

    void setAnswer(String answer) {
        this.answer = answer;
    }

    boolean isRight() {
        return right;
    }

    void setRight(boolean right) {
        this.right = right;
    }

    public String toString() {
        return "answer: " + this.answer + "; right = " + this.right + "\n";
    }
}
