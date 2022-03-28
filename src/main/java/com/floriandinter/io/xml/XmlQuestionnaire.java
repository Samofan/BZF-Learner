package com.floriandinter.io.xml;

import com.floriandinter.model.Question;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root
public class XmlQuestionnaire {
    @ElementList
    private List<XmlQuestion> xmlQuestions;

    public XmlQuestionnaire() {

    }

    public void setQuestions(List<Question> questions) {
        this.xmlQuestions = XmlQuestion.createXmlQuestions(questions);
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        for (XmlQuestion xmlQuestion : xmlQuestions) {
            questions.add(xmlQuestion.toQuestion());
        }

        return questions;
    }
}
