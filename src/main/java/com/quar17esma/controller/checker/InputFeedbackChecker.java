package com.quar17esma.controller.checker;

import com.quar17esma.exceptions.WrongDataException;

public class InputFeedbackChecker extends InputDataChecker {
    private static final int MARK_MIN = 1;
    private static final int MARK_MAX = 5;
    private static final int COMMENT_LENGTH_MAX = 500;

    public void checkData(int mark, String comment) throws WrongDataException {
        if (!isMarkCorrect(mark)) {
            throw new WrongDataException("Wrong mark");
        }
        if (!isCommentCorrect(comment)) {
            throw new WrongDataException("Wrong comment");
        }
    }

    private boolean isCommentCorrect(String comment) {
        return checkString(comment, CheckPatterns.CHAR_DIGIT_PUNCT_EN_RU, COMMENT_LENGTH_MAX);
    }

    private boolean isMarkCorrect(int mark) {
        return isIntInRange(mark, MARK_MIN, MARK_MAX);
    }
}
