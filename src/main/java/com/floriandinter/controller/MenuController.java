package com.floriandinter.controller;

import com.floriandinter.io.exception.QuestionListEmptyException;
import com.floriandinter.util.AlertUtil;
import com.floriandinter.util.QuestionHandler;
import com.floriandinter.util.WindowUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController  implements Initializable {
    private static final Logger LOG = LoggerFactory.getLogger(MenuController.class.getSimpleName());

    private final WindowUtil windowUtil = WindowUtil.getInstance();

    public VBox startVBox;
    public Button startButton;
    public Button exitButton;
    public Button optionButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startButton.prefWidthProperty().bind(startVBox.widthProperty());
        exitButton.prefWidthProperty().bind(startVBox.widthProperty());
        optionButton.prefWidthProperty().bind(startVBox.widthProperty());

        try {
            QuestionHandler questionHandler = new QuestionHandler();
        } catch (QuestionListEmptyException e) {
            startButton.setDisable(true);
            AlertUtil.createInformationDialog(
                    resourceBundle.getString("ALERT.TITLE.NO_QUESTIONS_LOADED"),
                    resourceBundle.getString("ALERT.HEADER.NO_QUESTIONS_LOADED"),
                    resourceBundle.getString("ALERT.CONTENT.NO_QUESTIONS_LOADED")).showAndWait();
        }
    }

    public void startQuizAction(ActionEvent actionEvent) {
        try {
            windowUtil.showQuiz(getStage());
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public void settingsAction(ActionEvent actionEvent) {
        try {
            windowUtil.showSettings(getStage());
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public void exitApplicationAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    private Stage getStage() {
        return (Stage) startVBox.getScene().getWindow();
    }
}
