package com.floriandinter.controller;

import com.floriandinter.io.pdf.PdfReader;
import com.floriandinter.model.Question;
import com.floriandinter.util.AlertUtil;
import com.floriandinter.util.FileLocations;
import com.floriandinter.util.QuestionHandler;
import com.floriandinter.util.WindowUtil;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    private static final Logger LOG = LoggerFactory.getLogger(SettingsController.class.getSimpleName());

    private final WindowUtil windowUtil = WindowUtil.getInstance();
    private ResourceBundle resourceBundle;

    public VBox settingsVbox;
    public Button readPdfButton;
    public Button backToMenuButton;

    //TODO: Add more options

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readPdfButton.prefWidthProperty().bind(settingsVbox.widthProperty());
        backToMenuButton.prefWidthProperty().bind(settingsVbox.widthProperty());
        this.resourceBundle = resourceBundle;
    }

    public void readPdfAction(ActionEvent actionEvent) {
        //TODO: Add explanation where to get the file
        try {
            File file = chooseFile();

            if (file != null) {
                int firstPageWithQuestions = requestFirstPageWithQuestions();
                parseAndSaveFile(file, firstPageWithQuestions);
                AlertUtil.createInformationDialog(
                        resourceBundle.getString("ALERT.TITLE.QUESTIONS_SAVED"),
                        resourceBundle.getString("ALERT.HEADER.QUESTIONS_SAVED"),
                        resourceBundle.getString("ALERT.CONTENT.QUESTIONS_SAVED")).show();
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            AlertUtil.createExceptionDialog(
                    resourceBundle.getString("ALERT.TITLE.GENERIC_ERROR"),
                    resourceBundle.getString("ALERT.HEADER.GENERIC_ERROR"),
                    resourceBundle.getString("ALERT.CONTENT.GENERIC_ERROR"),
                    e).show();
        }
    }

    private File chooseFile() {
        FileChooser fileChooser = configureFileChooser();
        return fileChooser.showOpenDialog(getStage());
    }

    private FileChooser configureFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(resourceBundle.getString("FILE_CHOOSER.TITLE.CHOOSE_PDF"));
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
        );

        return fileChooser;
    }

    private int requestFirstPageWithQuestions() {
        int firstPageWithQuestions = 7;

        Optional<String> result = AlertUtil.createTextInputDialog(
                resourceBundle.getString("ALERT.TITLE.FIRST_PAGE_WITH_QUESTIONS"),
                resourceBundle.getString("ALERT.HEADER.FIRST_PAGE_WITH_QUESTIONS"),
                resourceBundle.getString("ALERT.CONTENT.FIRST_PAGE_WITH_QUESTIONS")).showAndWait();

        if (result.isPresent()) {
            try {
                firstPageWithQuestions = Integer.parseInt(result.get());
            } catch (NumberFormatException e) {
                LOG.error("{} is not a number", result.get());
                return requestFirstPageWithQuestions();
            }

        }

        return firstPageWithQuestions;
    }

    private void parseAndSaveFile(File file, int firstPageWithQuestions) throws Exception {
        File hiddenFolder = new File(FileLocations.HIDDEN_FOLDER);
        if (!hiddenFolder.exists()) {
            hiddenFolder.mkdir();
        }

        PdfReader pdfReader = new PdfReader(file.getAbsolutePath(), firstPageWithQuestions);
        List<Question> questions = pdfReader.getQuestions();
        QuestionHandler.saveQuestionsToFile(questions);
    }

    public void backToMenuAction(ActionEvent actionEvent) {
        try {
            windowUtil.showMenu(getStage());
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    private Stage getStage() {
        return (Stage) settingsVbox.getScene().getWindow();
    }
}
