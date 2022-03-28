package com.floriandinter.ui;

import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class QuestionToggleGroup extends ToggleGroup {
    private boolean answerHintsShowing = false;

    public QuestionToggleGroup(QuestionToggleButton...toggleButtons) {
        super();

        for (QuestionToggleButton toggleButton : toggleButtons) {
            toggleButton.setToggleGroup(this);
        }
    }

    public QuestionToggleButton getSelectedToggleButton() {
        return (QuestionToggleButton) getSelectedToggle();
    }

    public QuestionToggleButton getRightAnswerToggleButton() {
        for (Toggle toggle : getToggles()) {
            QuestionToggleButton toggleButton = (QuestionToggleButton) toggle;
            if (toggleButton.isRightAnswer()) {
                return toggleButton;
            }
        }

        return null;
    }

    public boolean isRightAnswerSelected() {
        return getSelectedToggleButton().isRightAnswer();
    }

    public void showAnswerHints() {
        answerHintsShowing = true;
        var selectedToggleButton = getSelectedToggleButton();

        if (selectedToggleButton.isRightAnswer()) {
            selectedToggleButton.setRightAnswerHint();
        } else {
            selectedToggleButton.setWrongAnswerHint();
            getRightAnswerToggleButton().setRightAnswerHint();
        }
    }

    public boolean isAnswerHintsShowing() {
        return answerHintsShowing;
    }

    public void reset() {
        for (Toggle toggle : getToggles()) {
            QuestionToggleButton toggleButton = (QuestionToggleButton) toggle;
            toggleButton.reset();
        }
        answerHintsShowing = false;
    }
}
