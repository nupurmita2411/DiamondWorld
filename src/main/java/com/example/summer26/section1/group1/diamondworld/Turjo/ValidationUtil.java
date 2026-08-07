package com.example.summer26.section1.group1.diamondworld.Turjo;

import java.util.regex.Pattern;

public final class ValidationUtil {

    private static final Pattern EMPLOYEE_ID = Pattern.compile("^\\d{7}$");
    private static final Pattern PASSWORD = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).{6,}$");
    private static final Pattern PHONE = Pattern.compile("^01[3-9]\\d{8}$");
    private static final Pattern EMAIL = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    private static final Pattern SAFE_SEARCH = Pattern.compile("^[\\w\\s\\-'.,]+$");
    private static final Pattern AUTH_CODE = Pattern.compile("^AUTH\\d{4}$");

    private ValidationUtil() {
    }

    public static boolean isValidEmployeeId(String id) {
        return id != null && EMPLOYEE_ID.matcher(id.trim()).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD.matcher(password).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE.matcher(phone.trim()).matches();
    }

    public static boolean isValidEmail(String email) {
        return email == null || email.isBlank() || EMAIL.matcher(email.trim()).matches();
    }

    public static boolean isValidSearchKeyword(String keyword) {
        return keyword != null && !keyword.isBlank()
                && keyword.length() <= 100
                && SAFE_SEARCH.matcher(keyword.trim()).matches();
    }

    public static boolean isPositiveNumber(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        try {
            return Double.parseDouble(value.trim()) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidMonthYear(int month, int year) {
        return month >= 1 && month <= 12 && year >= 2020 && year <= 2035;
    }

    public static boolean isValidAuthCode(String code) {
        return code != null && AUTH_CODE.matcher(code.trim()).matches();
    }

    public static boolean isValidPurityRange(double purity) {
        return purity >= 50 && purity <= 99.9;
    }

    public static boolean isValidRingSize(String size) {
        try {
            double s = Double.parseDouble(size);
            return s >= 3 && s <= 15;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}



