package com.beitshean.mylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerHomePageActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    DatabaseReference reff;
    String user_full_name;
    String uid;
    TextView full_name_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_home_page);

        full_name_edit_text = (TextView) findViewById(R.id.mhp_full_name_text_view);

        findViewById(R.id.mho_existing_orders_button).setOnClickListener(this);
        findViewById(R.id.mho_disconnect_button2).setOnClickListener(this);
        findViewById(R.id.mho_sum_materials_button).setOnClickListener(this);
        findViewById(R.id.mho_sum_cash_button).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_full_name = dataSnapshot.child("fullName").getValue().toString();
                full_name_edit_text.setText("שלום "+user_full_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.mho_existing_orders_button:
                startActivity(new Intent(ManagerHomePageActivity.this, ManagerExistingOrdersActivity.class));
                break;

            case R.id.mho_disconnect_button2:
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.mho_sum_materials_button:
                startActivity(new Intent(this, ManagerMaterialsActivity.class));
                break;

            case R.id.mho_sum_cash_button:
                startActivity(new Intent(this, ManagerCashActivity.class));
                break;
        }
    }
}