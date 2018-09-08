package com.quar17esma.controller.checker;

import com.quar17esma.exceptions.WrongDataException;

import java.time.LocalDate;

public class InputUserChecker extends InputDataChecker {
    private static final int NAME_LENGTH_MAX = 100;
    private static final int EMAIL_LENGTH_MAX = 100;
    private static final int PHONE_LENGTH_MAX = 30;
    private static final int PASSWORD_LENGTH_MAX = 30;
    private static final LocalDate BIRTH_DATE_MIN = LocalDate.of(1900, 1, 1);

    public void checkData(String name, String surname, String email, String phone, String password, LocalDate birthDate) throws WrongDataException {
        if (!isNameCorrect(name)) {
            throw new WrongDataException("Wrong name");
        }
        if (!isNameCorrect(surname)) {
            throw new WrongDataException("Wrong surname");
        }
        if (!isEmailCorrect(email)) {
            throw new WrongDataException("Wrong email");
        }
        if (!isPhoneCorrect(phone)) {
            throw new WrongDataException("Wrong phone");
        }
        if (!isPasswordCorrect(password)) {
            throw new WrongDataException("Wrong password");
        }
        if (!isBirthDateCorrect(birthDate)) {
            throw new WrongDataException("Wrong birthDate");
        }
    }

    public boolean isNameCorrect(String name) {
        return checkString(name, CheckPatterns.NAME_RU, NAME_LENGTH_MAX) ||
                checkString(name, CheckPatterns.NAME_EN, NAME_LENGTH_MAX);
    }

    public boolean isPasswordCorrect(String password) {
        return checkString(password, CheckPatterns.PASSWORD, PASSWORD_LENGTH_MAX);
    }

    public boolean isPhoneCorrect(String phone) {
        return checkString(phone, CheckPatterns.PHONE, PHONE_LENGTH_MAX);
    }

    public boolean isEmailCorrect(String email) {
        return checkString(email, CheckPatterns.EMAIL, EMAIL_LENGTH_MAX);
    }

    public boolean isBirthDateCorrect(LocalDate birthDate) {
        return birthDate.isBefore(LocalDate.now()) &&
                birthDate.isAfter(BIRTH_DATE_MIN);
    }
}
