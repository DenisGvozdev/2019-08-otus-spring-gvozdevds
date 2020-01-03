package ru.otus.spring.domain;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String number;
    private String question;
    private List<Answer> answerList = new ArrayList<>();

    public Question(String number, String question, List<String> variants, List<String> rightAnswers) {
        this.number = number;
        this.question = question;

        for (String variantAnswer : variants) {
            Answer answer = new Answer();
            answer.setAnswer(variantAnswer);
            answer.setRight(rightAnswers.contains(variantAnswer));
            answerList.add(answer);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("number: ").append(this.number);
        sb.append("; question = ").append(this.question);
        for (Answer answer : answerList) {
            sb.append(answer.toString());
        }
        return sb.toString();
    }

    public String getVariants() {
        StringBuilder sb = new StringBuilder();
        for (Answer itm : answerList) {
            sb.append(itm.getAnswer()).append(",");
        }
        String str = sb.toString();
        return StringUtils.isEmpty(str) ? "" : str.substring(0, str.length() - 1);
    }

    public boolean checkAnswer(String answer) {
        for (Answer itm : answerList) {
            if (answer.equalsIgnoreCase(itm.getAnswer()) && itm.isRight()) {
                return true;
            }
        }
        return false;
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
}
