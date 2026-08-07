package com.example.summer26.section1.group1.diamondworld.Turjo;


public final class Session {

    private static Employee currentUser;

    private Session() {
    }

    public static Employee getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Employee user) {
        currentUser = user;
    }

    public static void clear() {
        currentUser = null;
    }
}


