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

public class UserHomePageActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    DatabaseReference reff;
    String user_full_name;
    String uid;
    TextView full_name_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        findViewById(R.id.uho_make_new_order_button).setOnClickListener(this);
        findViewById(R.id.uho_my_orders_button).setOnClickListener(this);
        full_name_edit_text = (TextView) findViewById(R.id.uhp_full_name_text_view);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user_full_name = dataSnapshot.child("fullName").getValue().toString();
                full_name_edit_text.setText(user_full_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.uho_make_new_order_button:
                startActivity(new Intent(this, UserNewOrderTypesActivity.class));
                break;

            case R.id.uho_my_orders_button:
                startActivity(new Intent(this, UserExistingOrdersActivity.class));
                break;
        }
    }
}
