package com.quar17esma.controller.checker;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InputUserCheckerTest {
    private InputUserChecker userChecker = new InputUserChecker();
    private static final String[] PHONES_TRUE = new String[]{"+380672391234",
            "380672391234",
            "(067)2391234",
            "38(067)2391234",
            "38067239-12-34",
            "+38(067)239-12-34"};
    private static final String[] PHONES_FALSE = new String[]{"+3a80672391234",
            "38()0672391234",
            "38(067)",
            "",
            null};
    private static final String[] EMAILS_TRUE = new String[]{"mail@gmail.com",
            "mail@gmail.com.ua",
            "mail123@yandex.ukr.net",
            "123@gmail.com"};
    private static final String[] EMAILS_FALSE = new String[]{"mail.com",
            "mail@gmail.",
            "mail1234567890123456789012345678901234567890" +
                    "1234567890123456789012345678901234567890" +
                    "1234567890132456789012345678901234567890" +
                    "@gmail.com",
            "",
            null};
    private static final int BORDER_MAX = 25;
    private static final int BORDER_MIN = 10;
    private static final int[] IN_RANGE = new int[]{10, 25, 13, 16};
    private static final int[] OUT_RANGE = new int[]{9, 26, 0, 39, 100, Integer.MIN_VALUE, Integer.MAX_VALUE};
    private static final String[] STRINGS_FALSE = new String[]{"teststringteststring", "", null};
    private static final int STRING_LENGTH_MAX = 10;
    private static final LocalDate DATE_TRUE = LocalDate.of(1991, 10, 25);
    private static final LocalDate[] DATES_FALSE = new LocalDate[]{
            LocalDate.of(2150, 10, 25),
            LocalDate.of(1899, 12, 31),
            LocalDate.MIN,
            LocalDate.MAX
    };

    @Test
    public void isPhoneCorrectTrue() throws Exception {
        boolean result;
        for (String phone : PHONES_TRUE) {
            result = userChecker.isPhoneCorrect(phone);
            assertTrue(result);
        }
    }

    @Test
    public void isPhoneCorrectFalse() throws Exception {
        boolean result;
        for (String phone : PHONES_FALSE) {
            result = userChecker.isPhoneCorrect(phone);
            assertFalse(result);
        }
    }

    @Test
    public void isEmailCorrectTrue() throws Exception {
        boolean result;
        for (String email : EMAILS_TRUE) {
            result = userChecker.isEmailCorrect(email);
            assertTrue(result);
        }
    }

    @Test
    public void isEmailCorrectFalse() throws Exception {
        boolean result;
        for (String email : EMAILS_FALSE) {
            result = userChecker.isEmailCorrect(email);
            assertFalse(result);
        }
    }

    @Test
    public void isBirthDateCorrect() throws Exception {
        boolean result;

        result = userChecker.isBirthDateCorrect(DATE_TRUE);
        assertTrue(result);

        for (LocalDate date: DATES_FALSE) {
            result = userChecker.isBirthDateCorrect(date);
            assertFalse(result);
        }
    }

    @Test
    public void isIntInRange() throws Exception {
        boolean result;
        for (int number : IN_RANGE) {
            result = userChecker.isIntInRange(number, BORDER_MIN, BORDER_MAX);
            assertTrue(result);
        }
        for (int number : OUT_RANGE) {
            result = userChecker.isIntInRange(number, BORDER_MIN, BORDER_MAX);
            assertFalse(result);
        }
    }

    @Test
    public void checkString() throws Exception {
        boolean result;
        for (String str : STRINGS_FALSE) {
            result = userChecker.checkString(str, CheckPatterns.NAME_EN, STRING_LENGTH_MAX);
            assertFalse(result);
        }
    }
}