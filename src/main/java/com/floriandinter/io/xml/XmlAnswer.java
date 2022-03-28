package com.floriandinter.io.xml;

import com.floriandinter.model.Answer;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

import java.util.ArrayList;
import java.util.List;

public class XmlAnswer {
    @Element
    private String answerText;
    @Attribute
    private boolean isRightAnswer;

    public XmlAnswer() {
    }

    public XmlAnswer(Answer answer) {
        this.answerText = answer.getAnswerText();
        this.isRightAnswer = answer.isRightAnswer();
    }

    public Answer toAnswer() {
        return new Answer(this.answerText, this.isRightAnswer);
    }

    public static List<XmlAnswer> createAnswers(Answer[] answers) {
        List<XmlAnswer> xmlAnswers = new ArrayList<>();

        for (Answer answer : answers) {
            xmlAnswers.add(new XmlAnswer(answer));
        }

        return xmlAnswers;
    }
}
