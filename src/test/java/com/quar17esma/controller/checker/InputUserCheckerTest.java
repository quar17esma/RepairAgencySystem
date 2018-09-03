package com.quar17esma.controller.checker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InputUserCheckerTest {
    String[] phonesTrue;
    String[] phonesFalse;
    InputUserChecker userChecker;

    @Before
    public void setUp() throws Exception {
        phonesTrue = new String[]{"+380672391234",
                "380672391234",
                "(067)2391234",
                "38(067)2391234",
                "38067239-12-34",
                "+38(067)239-12-34"};
        phonesFalse = new String[]{"+3a80672391234",
                "38()0672391234",
                "38(067)"};
        userChecker = new InputUserChecker();
    }

    @Test
    public void isPhoneCorrectTrue() throws Exception {
        boolean result;
        for (String phone:phonesTrue) {
            result = userChecker.isPhoneCorrect(phone);
            assertTrue(result);
        }
    }

    @Test
    public void isPhoneCorrectFalse() throws Exception {
        boolean result;
        for (String phone:phonesFalse) {
            result = userChecker.isPhoneCorrect(phone);
            assertFalse(result);
        }
    }

}