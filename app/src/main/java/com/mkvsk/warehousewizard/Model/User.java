package com.mkvsk.warehousewizard.Model;

public class User {
    String fullName;
    String password;
    String email;
    String phoneNumber;

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public User(String fullName, String password, String email, String phoneNumber) {
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPasswordRepeat() {
        return password;
    }

    public void setPasswordRepeat(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
