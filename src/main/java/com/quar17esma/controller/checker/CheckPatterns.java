package com.quar17esma.controller.checker;

import java.util.regex.Pattern;

public interface CheckPatterns {
    Pattern NAME_EN = Pattern.compile("[A-Z][A-Za-z ]+");
    Pattern NAME_RU = Pattern.compile("[А-Я][А-Яа-я ]+");
    Pattern EMAIL = Pattern.compile("[a-zA-Z1-9\\-._]+@[a-z1-9]+(.[a-z1-9]+)+");
    Pattern PHONE = Pattern.compile("[+]?([\\d]+)?([(][\\d]+[)])?([\\d]+)?[-]?([\\d]+)?[-]?[\\d]+");
    Pattern CHAR_DIGIT_PUNCT_EN_RU = Pattern.compile("[A-ZА-Яa-zа-я0-9 _.,!\"'/:()]+");
}
