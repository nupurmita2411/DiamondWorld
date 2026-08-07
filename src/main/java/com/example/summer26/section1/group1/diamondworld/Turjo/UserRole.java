package com.example.summer26.section1.group1.diamondworld.Turjo;

public enum UserRole {
    BRANCH_MANAGER("Branch Manager"),
    SALES_EXECUTIVE("Sales Executive");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}




