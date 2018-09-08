package com.quar17esma.controller.checker;

import com.quar17esma.exceptions.WrongDataException;

public class InputApplicationChecker extends InputDataChecker {
    private static final int PRODUCT_LENGTH_MAX = 100;
    private static final int REPAIR_TYPE_LENGTH_MAX = 100;

    public void checkData(String product, String repairType) {
        if (!isProductCorrect(product)) {
            throw new WrongDataException("Wrong product");
        }
        if (!isRepairTypeCorrect(repairType)) {
            throw  new WrongDataException("Wrong repair type");
        }
    }

    private boolean isRepairTypeCorrect(String repairType) {
        return checkString(repairType, CheckPatterns.CHAR_DIGIT_PUNCT_EN_RU, REPAIR_TYPE_LENGTH_MAX);
    }

    private boolean isProductCorrect(String product) {
        return checkString(product, CheckPatterns.CHAR_DIGIT_PUNCT_EN_RU, PRODUCT_LENGTH_MAX);
    }
}
