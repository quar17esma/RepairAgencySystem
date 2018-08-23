package com.quar17esma.controller.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class LabelManager {
    public static final String DEFAULT_LOCALE = "en_US";
    private static final Locale LOCALE_EN_US = new Locale("en", "US");
    private static final Locale LOCALE_RU_RU = new Locale("ru", "RU");
    private static final ResourceBundle RESOURCE_BUNDLE_EN_US = ResourceBundle.getBundle("Labels", LOCALE_EN_US);
    private static final ResourceBundle RESOURCE_BUNDLE_RU_RU = ResourceBundle.getBundle("Labels", LOCALE_RU_RU);

    private static ResourceBundle resourceBundle = RESOURCE_BUNDLE_EN_US;

    private LabelManager(){}

    public static String getProperty(String key, String locale){
        setLocale(locale);
        return resourceBundle.getString(key);
    }

    private static void setLocale(String locale) {
        switch (locale) {
            case "en_US" : resourceBundle = RESOURCE_BUNDLE_EN_US;
            break;
            case "ru_RU" : resourceBundle = RESOURCE_BUNDLE_RU_RU;
            break;
        }
    }
}
