package com.beitshean.mylaundry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserNewOrderTypesActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_new_order_types);

        findViewById(R.id.unot_home_weight_button).setOnClickListener(this);
        findViewById(R.id.unot_laundry_weight_button).setOnClickListener(this);;
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.unot_home_weight_button:
                startActivity(new Intent(UserNewOrderTypesActivity.this, UserHomeWeightOrderActivity.class));
                break;

            case R.id.unot_laundry_weight_button:
                startActivity(new Intent(UserNewOrderTypesActivity.this, UserLaundryWeightOrderActivity.class));
                break;
        }
    }
}
