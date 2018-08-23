package com.quar17esma.controller.checker;

import java.time.LocalDate;

public class InputUserChecker extends InputDataChecker {
    private static final int NAME_LENGTH_MAX = 100;
    private static final int EMAIL_LENGTH_MAX = 100;

    public boolean isInputDataCorrect(String name, String surname, String email, String phone, LocalDate birthDate) {
        boolean result = false;

        if (isNameCorrect(name) &&
                isEmailCorrect(email) &&
                isBirthDateCorrect(birthDate)) {

            result = true;
        }

        return result;
    }

    private boolean isNameCorrect(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        return (isMatches(CheckPatterns.NAME_EN, name) ||
                isMatches(CheckPatterns.NAME_RU, name)) &&
                name.length() <= NAME_LENGTH_MAX;
    }

    private boolean isEmailCorrect(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        return isMatches(CheckPatterns.EMAIL, email) &&
                email.length() <= EMAIL_LENGTH_MAX;
    }

    private boolean isBirthDateCorrect(LocalDate birthDate) {
        boolean result = false;

        if (birthDate.isBefore(LocalDate.now())) {
            result = true;
        }

        return result;
    }
}
