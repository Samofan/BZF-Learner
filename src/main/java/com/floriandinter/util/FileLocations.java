package com.floriandinter.util;

import javafx.scene.image.Image;

public class FileLocations {
    public static final String HIDDEN_FOLDER = System.getProperty("user.home") + "/.bzf-learner";
    public static final String MENU_WINDOW = "/fxml/Menu.fxml";
    public static final String QUIZ_WINDOW = "/fxml/Quiz.fxml";
    public static final String SETTINGS_WINDOW = "/fxml/Settings.fxml";
    public static final String CSS_FILE = "/style/style.css";
    public static final String QUESTIONS_FILE = HIDDEN_FOLDER + "/questions.xml";
    public static final String RESOURCE_BUNDLE = "bundles/strings";
    private static final Image APPLICATION_ICON_16 = new Image("/images/headset16.png");
    private static final Image APPLICATION_ICON_32 = new Image("/images/headset32.png");
    private static final Image APPLICATION_ICON_64 = new Image("/images/headset64.png");
    public static final Image[] APPLICATION_ICONS = new Image[]{
       APPLICATION_ICON_16,
       APPLICATION_ICON_32,
       APPLICATION_ICON_64
    };
}
