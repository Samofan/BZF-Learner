package com.floriandinter.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AlertUtil {
    public static Alert createInformationDialog(String title, String header, String content) {
        return getDefaultDialog(Alert.AlertType.INFORMATION, title, header, content);
    }

    public static Alert createWarningDialog(String title, String header, String content) {
        return getDefaultDialog(Alert.AlertType.WARNING, title, header, content);
    }

    public static TextInputDialog createTextInputDialog(String title, String header, String content) {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle(title);
        textInputDialog.setHeaderText(header);
        textInputDialog.setContentText(content);

        Stage stage = (Stage) textInputDialog.getDialogPane().getScene().getWindow();
        stage.getIcons().addAll(FileLocations.APPLICATION_ICONS);

        return textInputDialog;
    }

    public static Alert createExceptionDialog(String title, String header, String content, Exception exception) {
        Alert alert = getDefaultDialog(Alert.AlertType.ERROR, title, header, content);

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Stack trace:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expandableContent = new GridPane();
        expandableContent.setMaxWidth(Double.MAX_VALUE);
        expandableContent.add(label, 0, 0);
        expandableContent.add(textArea, 0, 1);
        alert.getDialogPane().setExpandableContent(expandableContent);

        return alert;
    }

    private static Alert getDefaultDialog(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().addAll(FileLocations.APPLICATION_ICONS);

        return alert;
    }
}
