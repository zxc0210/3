package com.example.myapplication2.util;

public class RegexUtil {
    private static final String phone_regex = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
    private static final String number = "^[1-9]*[1-9][0-9]*$";

    public static boolean isPhone(String value) {
        return value.matches(phone_regex);
    }

    public static boolean isNumber(String value) {
        return value.matches(number);
    }
}
