package com.quar17esma.controller.checker;

import java.util.regex.Pattern;

public class InputApplicationChecker extends InputDataChecker {
    private static final int PRODUCT_LENGTH_MAX = 100;
    private static final int REPAIR_TYPE_LENGTH_MAX = 100;

    public boolean isInputDataCorrect(String product, String repairType) {
        return checkString(product, CheckPatterns.CHAR_DIGIT_PUNCT_EN_RU, PRODUCT_LENGTH_MAX) &&
                checkString(repairType, CheckPatterns.CHAR_DIGIT_PUNCT_EN_RU, REPAIR_TYPE_LENGTH_MAX);
    }

    private boolean checkString(String stringToCheck, Pattern pattern, int maxLength) {
        if (stringToCheck == null || stringToCheck.isEmpty()) {
            return false;
        }

        return isMatches(pattern, stringToCheck) &&
                stringToCheck.length() <= maxLength;
    }
}
