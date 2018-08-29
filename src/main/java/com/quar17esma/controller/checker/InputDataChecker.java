package com.quar17esma.controller.checker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class InputDataChecker {

    protected boolean isMatches(Pattern pattern, String string) {
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    protected boolean isIntInRange(int number, int min, int max) {
        return (number >= min && number <= max);
    }

    protected boolean checkString(String stringToCheck, Pattern pattern, int maxLength) {
        if (stringToCheck == null || stringToCheck.isEmpty()) {
            return false;
        }

        return isMatches(pattern, stringToCheck) &&
                stringToCheck.length() <= maxLength;
    }
}
