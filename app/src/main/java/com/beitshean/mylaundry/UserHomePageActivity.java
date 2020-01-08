package com.beitshean.mylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
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

    TextView full_name_text_view;
    private static final int REQUESTED_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        findViewById(R.id.uho_make_new_order_button).setOnClickListener(this);
        findViewById(R.id.uho_my_orders_button).setOnClickListener(this);
        findViewById(R.id.uho_disconnect_button).setOnClickListener(this);
        findViewById(R.id.uho_call_button).setOnClickListener(this);
        full_name_text_view = (TextView) findViewById(R.id.uhp_full_name_text_view);

        Intent intent = getIntent();
        String full_name = intent.getStringExtra(MainActivity.EXTRA_FULL_NAME);

        full_name_text_view.setText("שלום "+full_name);
    }

    private void makePhoneCall() {
        String number = "012345678";

        if(ContextCompat.checkSelfPermission(UserHomePageActivity.this, Manifest.permission.CALL_PHONE) != getPackageManager().PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(UserHomePageActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUESTED_CALL);
        }else{
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
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

            case R.id.uho_disconnect_button:
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.uho_call_button:
                makePhoneCall();
                break;
        }
    }
}