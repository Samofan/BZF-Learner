package com.floriandinter.model;

public class Answer {
    private final String answerText;
    private final boolean isRightAnswer;

    public Answer(String answerText, boolean isRightAnswer) {
        this.answerText = answerText;
        this.isRightAnswer = isRightAnswer;
    }

    public String getAnswerText() {
        return answerText;
    }

    public boolean isRightAnswer() {
        return isRightAnswer;
    }
}
