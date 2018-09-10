package com.quar17esma.controller.checker;

import com.quar17esma.exceptions.WrongDataException;

public class InputApplicationChecker extends InputDataChecker {
    private static final int PRODUCT_LENGTH_MAX = 100;
    private static final int REPAIR_TYPE_LENGTH_MAX = 100;

    public void checkDataAdd(String product, String repairType) {
        if (!isProductCorrect(product)) {
            throw new WrongDataException("Wrong product");
        } else if (!isRepairTypeCorrect(repairType)) {
            throw new WrongDataException("Wrong repair type");
        }
    }

    private boolean isRepairTypeCorrect(String repairType) {
        return checkString(repairType, CheckPatterns.CHAR_DIGIT_PUNCT_EN_RU, REPAIR_TYPE_LENGTH_MAX);
    }

    private boolean isProductCorrect(String product) {
        return checkString(product, CheckPatterns.CHAR_DIGIT_PUNCT_EN_RU, PRODUCT_LENGTH_MAX);
    }

    public void checkDataAccept(String applicationId, String price) {
        try {
            boolean isIdCorrect = isApplicationIdCorrect(applicationId);
            if (!isIdCorrect) {
                throw new WrongDataException("Wrong application id");
            }
        } catch (NumberFormatException e) {
            throw new WrongDataException("Wrong application id");
        }

        try {
            boolean isPriceCorrect = isPriceCorrect(price);
            if (!isPriceCorrect) {
                throw new WrongDataException("Wrong price");
            }
        } catch (NumberFormatException e) {
            throw new WrongDataException("Wrong price");
        }
    }

    private boolean isApplicationIdCorrect(String applicationId) throws NumberFormatException {
        boolean result = false;

        if (Long.parseLong(applicationId) > 0) {
            result = true;
        }

        return result;
    }

    private boolean isPriceCorrect(String price) {
        boolean result = false;

        if (Integer.parseInt(price) > 0) {
            result = true;
        }

        return result;
    }
}
