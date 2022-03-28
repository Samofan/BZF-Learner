package com.floriandinter;

import com.floriandinter.util.WindowUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class BzfLearnerUi extends Application {
    private static final Logger LOG = LoggerFactory.getLogger(BzfLearnerUi.class.getSimpleName());

    private final WindowUtil windowUtil = WindowUtil.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            windowUtil.showMenu(stage);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }
}
