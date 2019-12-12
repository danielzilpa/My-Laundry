package com.beitshean.mylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateNewAccountActivity extends AppCompatActivity implements View.OnClickListener {

    EditText private_name_edit_text, last_name_edit_text, email_edit_text, phone_number_edit_text, address_edit_text, password_edit_text, confirm_password_edit_text;
    private FirebaseAuth mAuth;
    Button create_account_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        email_edit_text = (EditText) findViewById(R.id.cna_email_edit_text);
        password_edit_text = (EditText) findViewById(R.id.cna_password_edit_text);
        confirm_password_edit_text = (EditText) findViewById(R.id.cna_confirm_password_edit_text);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.cna_create_account_button).setOnClickListener(this);

    }

    public void registerUser() {

        String email = email_edit_text.getText().toString().trim();
        String password = password_edit_text.getText().toString().trim();
        String confirm_password = confirm_password_edit_text.getText().toString().trim();

        if(email.isEmpty()) {
            email_edit_text.setError("אנא הכנס אימייל");
            email_edit_text.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            password_edit_text.setError("אנא הכנס סיסמא");
            password_edit_text.requestFocus();
            return;
        }

        if(confirm_password.isEmpty()) {
            confirm_password_edit_text.setError("אנא הכנס אמת סיסמא");
            confirm_password_edit_text.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_edit_text.setError("אנא הכנס כתובת מייל חוקית");
            email_edit_text.requestFocus();
            return;
        }

        if(password.length() < 6) {
            password_edit_text.setError("הסיסמא צריכה להיות בת 6 תווים לפחות");
            password_edit_text.requestFocus();
            return;
        }

        if(!confirm_password.equals(password)) {
            confirm_password_edit_text.setError("אימות סיסמא נכשל");
            confirm_password_edit_text.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "ההרשמה בוצעה בהצלחה", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.cna_create_account_button:
                registerUser();
                break;
        }
    }
}