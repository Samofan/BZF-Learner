package com.floriandinter.io.exception;

public class QuestionListEmptyException extends Exception{
    public QuestionListEmptyException() {
        super("Question list is empty. No questions loaded from the file system.");
    }
}
