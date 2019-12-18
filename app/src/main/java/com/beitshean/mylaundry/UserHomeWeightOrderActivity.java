package com.beitshean.mylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserHomeWeightOrderActivity extends AppCompatActivity implements View.OnClickListener{

    public static double price;

    EditText weight_edit_text;
    CheckBox is_ironing_check_box, is_delivery_check_box;
    TextView price_option_text_view;
    double weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_weight_order);

        findViewById(R.id.uhw_price_option_button).setOnClickListener(this);
        findViewById(R.id.uhw_make_order_button).setOnClickListener(this);

        price_option_text_view = findViewById(R.id.uhw_price_option_text_view);

        weight_edit_text = findViewById(R.id.uhw_weight_edit_text);

        is_ironing_check_box = findViewById(R.id.uhw_ironing_check_box);
        is_delivery_check_box = findViewById(R.id.uhw_delivery_check_box);
    }


    private void calculateOrderPrice() {

        weight = Double.valueOf(weight_edit_text.getText().toString());

        price = price + weight * 15;

        if (is_ironing_check_box.isChecked()) {
            price = price + weight * 10;
        }

        if (is_delivery_check_box.isChecked()) {
            price = price + 30;
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.uhw_price_option_button:
                calculateOrderPrice();
                price_option_text_view.setText(Double.toString(price));
                break;

            case R.id.uhw_make_order_button:
                weight = Double.valueOf(weight_edit_text.getText().toString());
                Order order = new Order(weight, is_ironing_check_box.isChecked(), is_delivery_check_box.isChecked(), price);

                String random = String.valueOf(System.currentTimeMillis());

                FirebaseDatabase.getInstance().getReference("Orders")
                        .child(random).setValue(order)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                break;
        }
    }
}

