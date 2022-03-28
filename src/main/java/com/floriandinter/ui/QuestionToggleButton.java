package com.floriandinter.ui;

import com.floriandinter.model.Answer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.ToggleButton;

public class QuestionToggleButton extends ToggleButton {
    private static final PseudoClass WRONG_ANSWER_STYLE = PseudoClass.getPseudoClass("wrong");
    private static final PseudoClass RIGHT_ANSWER_STYLE = PseudoClass.getPseudoClass("right");
    private final BooleanProperty displaysWrongAnswer;
    private final BooleanProperty displaysRightAnswer;

    private Answer answer;

    public QuestionToggleButton() {
        super();
        setWrapText(true);

        displaysWrongAnswer = new SimpleBooleanProperty(false);
        displaysWrongAnswer.addListener(event -> pseudoClassStateChanged(WRONG_ANSWER_STYLE, displaysWrongAnswer.get()));
        displaysRightAnswer = new SimpleBooleanProperty(false);
        displaysRightAnswer.addListener(event -> pseudoClassStateChanged(RIGHT_ANSWER_STYLE, displaysRightAnswer.get()));
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
        setText(answer.getAnswerText());
    }

    public boolean isRightAnswer() {
        return answer.isRightAnswer();
    }

    public void setWrongAnswerHint() {
        displaysWrongAnswer.set(true);
    }

    public void setRightAnswerHint() {
        displaysRightAnswer.set(true);
    }

    public void reset() {
        setSelected(false);
        displaysRightAnswer.set(false);
        displaysWrongAnswer.set(false);
    }
}
