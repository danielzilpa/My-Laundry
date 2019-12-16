package com.beitshean.mylaundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserHomePageActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        findViewById(R.id.uho_make_new_order_button).setOnClickListener(this);
        findViewById(R.id.uho_my_orders_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.uho_make_new_order_button:
                startActivity(new Intent(this, UserNewOrderTypesActivity.class));
                break;

            case R.id.uho_my_orders_button:
                break;
        }
    }
}
