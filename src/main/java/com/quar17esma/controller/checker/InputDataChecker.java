package com.quar17esma.controller.checker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class InputDataChecker {

     protected boolean isMatches(Pattern pattern, String string) {
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    protected boolean isIntInRange(int number, int min, int max) {
        boolean result = false;

        if (number >= min && number <= max) {
            result = true;
        }

        return result;
    }
}
