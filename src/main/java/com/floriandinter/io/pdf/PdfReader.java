package com.floriandinter.io.pdf;

import com.floriandinter.model.Answer;
import com.floriandinter.model.Question;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfReader {
    private final static Logger LOG = LoggerFactory.getLogger(PdfReader.class.getSimpleName());
    private static final String EMPTY_LINE = "  ";
    private static final String PDF_HEADER = "Prüfungsfragen im Prüfungsteil";
    private final PDDocument document;

    public PdfReader(String fileName, int firstPageWithQuestions) throws IOException {
        LOG.info("Opening {}. First page with question is {}", fileName, firstPageWithQuestions);
        this.document = PDDocument.load(new File(fileName));
        removeFirstPages(firstPageWithQuestions);
    }

    public List<Question> getQuestions() throws IOException {
        PDFTextStripper textStripper = new PDFTextStripper();
        String pdfText = textStripper.getText(document);

        return parseText(pdfText);
    }

    private void removeFirstPages(int firstPageWithQuestions) {
        for (int i = 0; i < firstPageWithQuestions - 1; i++) {
            document.removePage(0);
        }
    }

    private List<Question> parseText(String pdfText) {
        List<Question> questions = new ArrayList<>();
        String[] lines = pdfText.split("\\r?\\n");

        int questionNumber = 1;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].replaceAll(" ", "");
            String questionNumberString = String.valueOf(questionNumber);
            if (line.startsWith(questionNumberString)) {
                Question question = new Question(questionNumber, extractQuestionText(i, lines));
                questions.add(question);
                questionNumber++;
                question.addAnswers(extractAnswers(i, lines));
            }
        }

        return questions;
    }

    private String extractQuestionText(int i, String[] lines) {
        int counter = i;
        StringBuilder stringBuilder = new StringBuilder();

        while(!lines[counter].equals(EMPTY_LINE)) {
            stringBuilder.append(lines[counter]);
            counter++;
        }

        return removeQuestionNumberFromText(stringBuilder.toString());
    }

    private String removeQuestionNumberFromText(String questionText) {
        return questionText.replaceFirst("^[0-9]*", "");
    }

    private Answer[] extractAnswers(int i, String[] lines) {
        Answer[] answers = new Answer[4];
        int answerCounter = 0;
        int counter = i;

        StringBuilder stringBuilder = new StringBuilder();

        while(answerCounter < answers.length) {
            if (isAnswerText(lines[counter])) {
                while (isPartOfAnswer(lines[counter], answerCounter)) {
                    stringBuilder.append(lines[counter]);
                    counter++;
                }

                String answerText = stringBuilder.toString();
                stringBuilder.setLength(0);

                Answer answer = new Answer(removeAnswerPrefix(answerText), isRightAnswer(answerText));
                answers[answerCounter] = answer;
                answerCounter++;
            } else {
                counter++;
            }
        }

        return answers;
    }

    private boolean isAnswerText(String line) {
        return line.startsWith("A ") || line.startsWith("B ")
                || line.startsWith("C ") || line.startsWith("D ");
    }

    private boolean isPartOfAnswer(String line, int answerCounter) {
        return !line.equals(EMPTY_LINE) && !isNewAnswer(line, answerCounter) && !line.contains(PDF_HEADER);
    }

    private boolean isNewAnswer(String line, int answerCounter) {
        return line.startsWith(getNewAnswerStart(answerCounter));
    }

    private String getNewAnswerStart(int answerCounter) {
        return switch (answerCounter) {
            case 0 -> "B ";
            case 1 -> "C ";
            case 2 -> "D ";
            default -> EMPTY_LINE;
        };
    }

    private String removeAnswerPrefix(String answerText) {
        return answerText.replaceFirst("^[A-D]*", "");
    }

    private boolean isRightAnswer(String answerText) {
        return answerText.startsWith("A ");
    }
}
