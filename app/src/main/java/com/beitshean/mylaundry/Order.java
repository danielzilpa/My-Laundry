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
    public String order_status, user_id;

    public Order () {

    }

    public Order(double weight, boolean is_ironing, boolean is_delivery, double price, String user_id) {
        this.weight = weight;
        this.is_ironing = is_ironing;
        this.is_delivery = is_delivery;
        this.price = price;
        this.order_status = "ההזמנה התקבלה";
        this.user_id = user_id;
    }

    public Order(boolean is_ironing, boolean is_delivery, double price, String user_id) {
        this.is_ironing = is_ironing;
        this.is_delivery = is_delivery;
        this.price = price;
        this.order_status = "ההזמנה התקבלה";
        this.user_id = user_id;
    }

    public Order (Order other) {
        this.is_ironing = other.is_ironing;
        this.is_delivery = other.is_delivery;
        this.price = other.price;
        this.order_status = new String(other.order_status);
        this.user_id = other.user_id;
    }

    public String toString() {

        if (is_delivery && is_ironing) {
            return "סטטוס ההזמנה: " + this.order_status + "\n" +
                    "פרטי ההזמנה:\n" +
                    "משקל: " + this.weight + "\n" +
                    "כולל גיהוץ ומשלוח\n" +
                    "מחיר: " + this.price + "\n";

        } else if (!is_delivery && is_ironing) {
            return "סטטוס ההזמנה: " + this.order_status + "\n" +
                    "פרטי ההזמנה:\n" +
                    "משקל: " + this.weight + "\n" +
                    "כולל גיהוץ ולא כולל משלוח\n" +
                    "מחיר: " + this.price + "\n";

        } else if (is_delivery && !is_ironing) {
            return "סטטוס ההזמנה: " + this.order_status + "\n" +
                    "פרטי ההזמנה:\n" +
                    "משקל: " + this.weight + "\n" +
                    "כולל משלוח ולא כולל גיהוץ\n" +
                    "מחיר: " + this.price + "\n";

        } else {
            return "סטטוס ההזמנה: " + this.order_status + "\n" +
                    "פרטי ההזמנה:\n" +
                    "משקל: " + this.weight + "\n" +
                    "לא כולל גיהוץ ומשלוח\n" +
                    "מחיר: " + this.price + "\n";
        }
    }

}