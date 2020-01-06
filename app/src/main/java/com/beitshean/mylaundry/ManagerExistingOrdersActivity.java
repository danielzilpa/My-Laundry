package com.beitshean.mylaundry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManagerExistingOrdersActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    EditText order_number_edit_text;
    String updated_status;
    TextView update_successful_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_existing_orders);

        update_successful_text_view = findViewById(R.id.meo_update_successful_text_view);
        order_number_edit_text = findViewById(R.id.meo_order_number_edit_text);
        findViewById(R.id.meo_update_status_button).setOnClickListener(this);

        Spinner spinner = findViewById(R.id.meo_status_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.order_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        listView = (ListView) findViewById(R.id.meo_list_view);
        arrayAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(Order.class).toString();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updated_status = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void updateOrderStatus() {
        String order_id = order_number_edit_text.getText().toString().trim();

        FirebaseDatabase.getInstance().getReference("Orders")
                .child(order_id).child("order_status").setValue(updated_status);

        update_successful_text_view.setText("העדכון בוצע בהצלחה");
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {

            case R.id.meo_update_status_button:
                updateOrderStatus();
        }
    }
}
