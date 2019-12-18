package com.beitshean.mylaundry;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class Order {

    public double price;
    public double weight = 0;
    public boolean is_ironing, is_delivery;
    public String order_status, user_email;

    FirebaseAuth mAuth;
    DatabaseReference reff;
    String user_full_name;
    String uid;

    public Order(double weight, boolean is_ironing, boolean is_delivery, double price) {
        this.weight = weight;
        this.is_ironing = is_ironing;
        this.is_delivery = is_delivery;
        this.price = price;
        this.order_status = "ההזמנה התקבלה";
        this.user_email = getUserEmail();

        //System.out.println("The user email is: " + user_email);

    }

    public Order(boolean is_ironing, boolean is_delivery, double price) {
        this.is_ironing = is_ironing;
        this.is_delivery = is_delivery;
        this.price = price;
        this.order_status = "ההזמנה התקבלה";
        this.user_email = getUserEmail();
    }

    private String getUserEmail() {

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_email = dataSnapshot.child("email").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return user_email;
    }
}