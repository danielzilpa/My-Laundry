package com.beitshean.mylaundry;

public class User {

    public String full_name, email, phone, address;
    public boolean isManager;

    public User(String full_name, String email, String phone) {
        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.isManager = false;
        this.address = null;
    }
}
