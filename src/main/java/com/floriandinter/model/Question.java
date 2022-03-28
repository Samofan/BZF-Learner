package com.floriandinter.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Question {
    private final int questionNumber;
    private final String questionText;
    private final Answer[] answers;

    public Question(int questionNumber, String questionText, Answer[] answers) {
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.answers = answers;
    }

    public Question(int questionNumber, String questionText) {
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.answers = new Answer[4];
    }

    public void addAnswer(Answer answer) {
        for (int i = 0; i < answers.length; i++) {
            if (answers[i] == null) {
                answers[i] = answer;
                return;
            }
        }
    }

    public void addAnswers(Answer[] answers) {
        for (Answer answer : answers) {
            addAnswer(answer);
        }
    }

    public void shuffleAnswers() {
        List<Answer> answerList = Arrays.asList(this.answers);
        Collections.shuffle(answerList);

        for (int i = 0; i < answers.length; i++) {
            this.answers[i] = answerList.get(i);
        }
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Answer[] getAnswers() {
        return answers;
    }
}
