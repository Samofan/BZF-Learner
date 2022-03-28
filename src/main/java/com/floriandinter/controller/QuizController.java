package com.floriandinter.controller;

import com.floriandinter.io.exception.QuestionListEmptyException;
import com.floriandinter.model.Question;
import com.floriandinter.ui.QuestionToggleButton;
import com.floriandinter.ui.QuestionToggleGroup;
import com.floriandinter.util.AlertUtil;
import com.floriandinter.util.QuestionHandler;
import com.floriandinter.util.WindowUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuizController implements Initializable {
    private static final Logger LOG = LoggerFactory.getLogger(QuizController.class.getSimpleName());

    public QuestionToggleGroup toggleGroup;
    private final IntegerProperty amountAnsweredQuestions = new SimpleIntegerProperty(0);
    private final IntegerProperty amountWrong = new SimpleIntegerProperty(0);
    private final IntegerProperty amountRight = new SimpleIntegerProperty(0);

    private QuestionHandler questionHandler;
    private final WindowUtil windowUtil = WindowUtil.getInstance();

    public QuestionToggleButton aToggle;
    public QuestionToggleButton bToggle;
    public QuestionToggleButton cToggle;
    public QuestionToggleButton dToggle;
    public Text questionLabel;
    public TextFlow questionTextFlow;
    public Button nextQuestionButton;
    public Button backToMenuButton;
    public Text amountAnsweredText;
    public Text amountRightText;
    public Text amountWrongText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleGroup = new QuestionToggleGroup(aToggle, bToggle, cToggle, dToggle);
        aToggle.prefWidthProperty().bind(questionTextFlow.widthProperty());
        bToggle.prefWidthProperty().bind(questionTextFlow.widthProperty());
        cToggle.prefWidthProperty().bind(questionTextFlow.widthProperty());
        dToggle.prefWidthProperty().bind(questionTextFlow.widthProperty());
        nextQuestionButton.prefWidthProperty().bind(questionTextFlow.widthProperty());
        backToMenuButton.prefWidthProperty().bind(questionTextFlow.widthProperty());
        amountAnsweredText.textProperty().bind(amountAnsweredQuestions.asString());
        amountRightText.textProperty().bind(amountRight.asString());
        amountWrongText.textProperty().bind(amountWrong.asString());

        try {
            questionHandler = new QuestionHandler();
            setQuestion();
        } catch (QuestionListEmptyException e) {
            LOG.warn("Question list is empty: {}", e.getMessage());
            AlertUtil.createInformationDialog(
                    resourceBundle.getString("ALERT.TITLE.NO_QUESTIONS_LOADED"),
                    resourceBundle.getString("ALERT.HEADER.NO_QUESTIONS_LOADED"),
                    resourceBundle.getString("ALERT.CONTENT.NO_QUESTIONS_LOADED")).showAndWait();
        }
    }

    public void nextQuestionAction(ActionEvent actionEvent) {
        if (toggleGroup.getSelectedToggle() != null) {
            if (toggleGroup.isAnswerHintsShowing()) {
                toggleGroup.reset();
                setQuestion();
            } else {
                if (toggleGroup.isRightAnswerSelected()) {
                    amountRight.set(amountRight.get() + 1);
                    toggleGroup.reset();
                    amountAnsweredQuestions.set(amountWrong.get() + amountRight.get());
                    setQuestion();
                } else {
                    amountWrong.set(amountWrong.get() + 1);
                    amountAnsweredQuestions.set(amountWrong.get() + amountRight.get());
                    toggleGroup.showAnswerHints();
                }
            }
        }
    }

    public void backToMenuAction(ActionEvent actionEvent) {
        try {
            windowUtil.showMenu(getStage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setQuestion() {
        Question question = questionHandler.getQuestion();
        questionLabel.setText(question.getQuestionNumber() + ". " + question.getQuestionText());
        aToggle.setAnswer(question.getAnswers()[0]);
        bToggle.setAnswer(question.getAnswers()[1]);
        cToggle.setAnswer(question.getAnswers()[2]);
        dToggle.setAnswer(question.getAnswers()[3]);
    }

    private Stage getStage() {
        return (Stage) questionLabel.getScene().getWindow();
    }
}
