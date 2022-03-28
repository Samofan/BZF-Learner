package com.floriandinter.util;

import com.floriandinter.io.exception.QuestionListEmptyException;
import com.floriandinter.io.xml.XmlQuestionnaire;
import com.floriandinter.model.Question;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionHandler {
    public static final Logger LOG = LoggerFactory.getLogger(QuestionHandler.class.getSimpleName());

    private static final File QUESTIONS_FILE = new File(FileLocations.QUESTIONS_FILE);
    private final List<Question> questions = new ArrayList<>();
    private int counter = 0;

    public QuestionHandler() throws QuestionListEmptyException {
        questions.addAll(loadQuestionsFromFile());
    }

    public List<Question> loadQuestionsFromFile() throws QuestionListEmptyException {
        List<Question> questions = new ArrayList<>();
        Serializer serializer = new Persister();

        try {
            XmlQuestionnaire xmlQuestionnaire = serializer.read(XmlQuestionnaire.class, QUESTIONS_FILE);
            questions.addAll(xmlQuestionnaire.getQuestions());
        } catch (Exception e) {
            LOG.error("Error serializing: {}", e.getMessage());
        }

        if (questions.size() < 1) {
            throw new QuestionListEmptyException();
        }

        Collections.shuffle(questions);

        for (Question question : questions) {
            question.shuffleAnswers();
        }

        LOG.info("Loaded {} questions from file {}", questions.size(), QUESTIONS_FILE.getName());

        return questions;
    }

    public static void saveQuestionsToFile(List<Question> questions) throws Exception {
        LOG.info("Saving {} questions to file {}", questions.size(), QUESTIONS_FILE.getName());
        XmlQuestionnaire questionnaire = new XmlQuestionnaire();
        questionnaire.setQuestions(questions);

        Serializer serializer = new Persister();
        serializer.write(questionnaire, QUESTIONS_FILE);
    }

    public Question getQuestion() {
        if (counter < questions.size() - 1) {
            counter++;
            return questions.get(counter);
        } else {
            counter = 0;
            return getQuestion();
        }
    }

    public int getAmountOfQuestions() {
        return questions.size();
    }
}
