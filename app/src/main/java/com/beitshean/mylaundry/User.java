package com.beitshean.mylaundry;

public class User {

    private String full_name, email, phone, address;
    private boolean isManager;

    public User() {

    }

    public User(String full_name, String email, String phone) {
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.address = null;
        this.isManager = false;
    }

    public String getFullName() {
        return full_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }
}
