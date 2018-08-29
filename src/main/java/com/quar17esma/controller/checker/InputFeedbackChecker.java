package com.quar17esma.controller.checker;

public class InputFeedbackChecker extends InputDataChecker {
    private static final int MARK_MIN = 1;
    private static final int MARK_MAX = 5;
    private static final int COMMENT_LENGTH_MAX = 500;

    public boolean isInputDataCorrect(int mark, String comment) {
      return isIntInRange(mark, MARK_MIN, MARK_MAX) &&
                checkString(comment, CheckPatterns.CHAR_DIGIT_PUNCT_EN_RU, COMMENT_LENGTH_MAX);
    }
}
