package com.floriandinter.io.xml;

import com.floriandinter.model.Question;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.List;


public class XmlQuestion {
    @Attribute
    private int questionNumber;
    @Element
    private String questionText;
    @ElementList
    private List<XmlAnswer> answers;

    public XmlQuestion() {

    }

    public XmlQuestion(Question question) {
        this.questionNumber = question.getQuestionNumber();
        this.questionText = question.getQuestionText();
        this.answers = XmlAnswer.createAnswers(question.getAnswers());
    }

    public static List<XmlQuestion> createXmlQuestions(List<Question> questions) {
        List<XmlQuestion> xmlQuestions = new ArrayList<>();

        for (Question question : questions) {
            xmlQuestions.add(new XmlQuestion(question));
        }
        return xmlQuestions;
    }

    public Question toQuestion() {
        Question question = new Question(this.questionNumber, this.questionText);

        for (XmlAnswer xmlAnswer : answers) {
            question.addAnswer(xmlAnswer.toAnswer());
        }

        return question;
    }
}
