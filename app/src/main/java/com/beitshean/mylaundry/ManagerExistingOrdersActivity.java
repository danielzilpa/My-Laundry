package com.beitshean.mylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManagerExistingOrdersActivity extends AppCompatActivity {

    DatabaseReference reff;
    ListView order_list_view;
    List<Order> order_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_existing_orders);

        reff = FirebaseDatabase.getInstance().getReference("Orders");
        order_list_view = findViewById(R.id.meo_existing_orders_list_view);
        order_list = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                order_list.clear();
                for (DataSnapshot orders_shapshot : dataSnapshot.getChildren()) {
                    Order order = orders_shapshot.getValue(Order.class);
                    order_list.add(order);
                }

                OrderList adapter = new OrderList(ManagerExistingOrdersActivity.this, order_list);
                order_list_view.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
