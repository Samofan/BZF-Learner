package com.floriandinter.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class WindowUtil {

    private static final WindowUtil INSTANCE = new WindowUtil();

    private final ResourceBundle resourceBundle;

    private WindowUtil() {
        this.resourceBundle = ResourceBundle.getBundle(FileLocations.RESOURCE_BUNDLE, Locale.getDefault());
    }

    public static WindowUtil getInstance() {
        return INSTANCE;
    }

    public void showMenu(Stage stage) throws IOException {
        Parent root = getParent(FileLocations.MENU_WINDOW);
        changeStage(root, stage);
    }

    public void showQuiz(Stage stage) throws IOException {
        Parent root = getParent(FileLocations.QUIZ_WINDOW);
        changeStage(root, stage);
    }

    public void showSettings(Stage stage) throws IOException {
        Parent root = getParent(FileLocations.SETTINGS_WINDOW);
        changeStage(root, stage);
    }

    private Parent getParent(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setResources(this.resourceBundle);

        return loader.load();
    }

    private void changeStage(Parent root, Stage stage) {
        Scene scene = new Scene(root);
        scene.getStylesheets().add(FileLocations.CSS_FILE);
        stage.setScene(scene);
        stage.getIcons().addAll(FileLocations.APPLICATION_ICONS);
        stage.show();
    }
}
