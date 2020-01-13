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

    FirebaseAuth mAuth;
    EditText weight_edit_text;
    CheckBox is_ironing_check_box, is_delivery_check_box;
    TextView price_option_text_view;
    double weight;
    String uid;
    TextView order_complete_text_view, follow_order_text_view;


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
        order_complete_text_view = findViewById(R.id.uhw_order_complete_edit_text);
        follow_order_text_view = findViewById(R.id.uhw_follow_order_edit_text);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
    }

    private void calculateOrderPrice() {

        try{
            Double.parseDouble(weight_edit_text.getText().toString());
        }catch (Exception e) {
            weight_edit_text.setError("אנא הכנס משקל תקין");
            weight_edit_text.requestFocus();
            return;
        }

        weight = Double.valueOf(weight_edit_text.getText().toString());

        if(weight<0) {
            weight_edit_text.setError("אנא הכנס משקל תקין");
            weight_edit_text.requestFocus();
            return;
        }

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
                String order_id = String.valueOf(System.currentTimeMillis());
                Order order = new Order(weight, is_ironing_check_box.isChecked(), is_delivery_check_box.isChecked(), price, uid, order_id);

                FirebaseDatabase.getInstance().getReference("Orders")
                        .child(order_id).setValue(order)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                price_option_text_view.setText("");
                order_complete_text_view.setText("ההזמנה התקבלה");
                follow_order_text_view.setText("ניתן לעקוב אחריה תחת ההזמנות שלי");
                break;
        }
    }
}

