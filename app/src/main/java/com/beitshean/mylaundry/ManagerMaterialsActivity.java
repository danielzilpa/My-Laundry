package com.beitshean.mylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManagerMaterialsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    TextView actual_gel_sum_text_view, actual_softener_sum_text_view, actual_stain_remover_sum_text_view,update_done_text_view;
    EditText update_sum_number_edit_text;
    String sum_gel, sum_softener, sum_stain_remover, selected_material;
    FirebaseAuth mAuth;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_materials);

        actual_gel_sum_text_view = findViewById(R.id.materials_actual_gel_sum_text_view);
        actual_softener_sum_text_view = findViewById(R.id.materials_actual_softener_sum_text_view);
        actual_stain_remover_sum_text_view = findViewById(R.id.materials_actual_stain_remover_sum_text_view);
        update_sum_number_edit_text = findViewById(R.id.materials_update_sum_number_edit_text);
        update_done_text_view = findViewById(R.id.materials_update_done_text_view);
        findViewById(R.id.materials_update_button).setOnClickListener(this);

        Spinner spinner = findViewById(R.id.materials_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.materials, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("Materials");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sum_gel =  dataSnapshot.child("gel").getValue().toString().trim();
                sum_softener = dataSnapshot.child("softener").getValue().toString().trim();
                sum_stain_remover = dataSnapshot.child("stain_remover").getValue().toString().trim();

                actual_gel_sum_text_view.setText(sum_gel);
                actual_softener_sum_text_view.setText(sum_softener);
                actual_stain_remover_sum_text_view.setText(sum_stain_remover);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void updateMaterials() {

         String sum_update = update_sum_number_edit_text.getText().toString().trim();

        if(selected_material.equals("גל כביסה")) {
            selected_material = "gel";
        }
        else if (selected_material.equals("מרכך כביסה")) {
            selected_material = "softener";
        }

        else {
            selected_material = "stain_remover";
        }

        FirebaseDatabase.getInstance().getReference("Materials")
                .child(selected_material).setValue(sum_update);

        if(selected_material.equals("gel")) {
            actual_gel_sum_text_view.setText(sum_update);
        }
        else if (selected_material.equals("softener")) {
            actual_softener_sum_text_view.setText(sum_update);
        }

        else {
            actual_stain_remover_sum_text_view.setText(sum_update);
        }

        update_done_text_view.setText("העדכון בוצע בהצלחה");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.materials_update_button:
                updateMaterials();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selected_material = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
