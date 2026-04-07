package com.turkcell;

import java.math.BigDecimal;
import java.util.UUID;

public class Customer {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private BigDecimal balance;

    public Customer(String name, String email, String phoneNumber) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.balance = BigDecimal.ZERO;
    }

    public Customer() {}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new RuntimeException("İsim boş olamaz");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new RuntimeException("Email boş olamaz");
        }
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}