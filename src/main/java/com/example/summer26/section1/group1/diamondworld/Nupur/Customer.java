package com.example.summer26.section1.group1.diamondworld.Nupur;


import java.io.Serializable;

public class Customer implements Serializable {

    private String name;
    private String phone;
    private String email;
    private String address;
    private String membership;
    private String preferences;

    {
    }

    public Customer(String name, String phone, String email, String address, String membership, String preferences) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.membership = membership;
        this.preferences = preferences;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", membership='" + membership + '\'' +
                ", preferences='" + preferences + '\'' +
                '}';
    }
}
