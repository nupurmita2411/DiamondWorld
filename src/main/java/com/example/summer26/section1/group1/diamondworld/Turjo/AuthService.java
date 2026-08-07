package com.example.summer26.section1.group1.diamondworld.Turjo;


import java.util.Optional;

public final class AuthService {

    private AuthService() {
    }

    public static Optional<String> validateLoginInput(String employeeId, String password) {
        if (!ValidationUtil.isValidEmployeeId(employeeId)) {
            return Optional.of("Invalid Employee ID. Must be a 7-digit numeric ID. (VL)");
        }
        if (!ValidationUtil.isValidPassword(password)) {
            return Optional.of("Invalid password. Min 6 chars with letters and digits. (VL)");
        }
        return Optional.empty();
    }

    public static Optional<Employee> authenticate(String employeeId, String password) {
        Optional<String> validation = validateLoginInput(employeeId, password);
        if (validation.isPresent()) {
            return Optional.empty();
        }
        return DataStore.getInstance().findEmployee(employeeId, password);
    }

    public static void signOut() {
        Session.clear();
    }
}




