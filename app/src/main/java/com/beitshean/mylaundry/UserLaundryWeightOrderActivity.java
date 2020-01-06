package com.beitshean.mylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserLaundryWeightOrderActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    CheckBox is_ironing_check_box, is_delivery_check_box;
    String uid;
    TextView order_complete_text_view, follow_order_text_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_laundry_weight_order);

        findViewById(R.id.ulw_make_order_button).setOnClickListener(this);

        is_ironing_check_box = findViewById(R.id.ulw_ironing_check_box);
        is_delivery_check_box = findViewById(R.id.ulw_delivery_check_box);
        order_complete_text_view = findViewById(R.id.ulw_order_complete_edit_text);
        follow_order_text_view = findViewById(R.id.ulw_follow_order_edit_text);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ulw_make_order_button:
                Order order = new Order(is_ironing_check_box.isChecked(), is_delivery_check_box.isChecked(), uid);

                String order_id = String.valueOf(System.currentTimeMillis());

                FirebaseDatabase.getInstance().getReference("Orders")
                        .child(order_id).setValue(order)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                order_complete_text_view.setText("ההזמנה התקבלה");
                follow_order_text_view.setText("ניתן לעקוב אחריה תחת ההזמנות שלי");

                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
