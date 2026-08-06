package com.example.summer26.section1.group1.diamondworld.Turjo;

public class Employee {
    private String id;
    private String name;
    private UserRole role;
    private String password;

    public Employee() {
    }

    public Employee(String id, String name, UserRole role, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}




