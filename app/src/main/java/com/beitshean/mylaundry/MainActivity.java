package com.beitshean.mylaundry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_FULL_NAME = "com.beitshean.mylaundry.EXTRA_FULL_NAME";

    EditText full_name_edit_text, phone_edit_text, email_edit_text, password_edit_text, confirm_password_edit_text;
    private FirebaseAuth mAuth;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        full_name_edit_text = findViewById(R.id.cna_full_name_edit_text);
        phone_edit_text = findViewById(R.id.cna_phone_edit_text);
        email_edit_text = findViewById(R.id.cna_email_edit_text);
        password_edit_text = findViewById(R.id.cna_password_edit_text);
        confirm_password_edit_text = findViewById(R.id.cna_confirm_password_edit_text);
        progress_bar = findViewById(R.id.cna_progress_bar);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.login_create_account_button).setOnClickListener(this);
        findViewById(R.id.login_already_have_account_button).setOnClickListener(this);

        progress_bar.setVisibility(View.INVISIBLE);
    }

    public void registerUser() {

        final String full_name = full_name_edit_text.getText().toString().trim();
        final String phone = phone_edit_text.getText().toString().trim();
        final String email = email_edit_text.getText().toString().trim();
        String password = password_edit_text.getText().toString().trim();
        String confirm_password = confirm_password_edit_text.getText().toString().trim();

        if(full_name.isEmpty()) {
            full_name_edit_text.setError("אנא הכנס שם מלא");
            full_name_edit_text.requestFocus();
            return;
        }

        if(phone.isEmpty()) {
            phone_edit_text.setError("אנא הכנס מספר טלפון");
            phone_edit_text.requestFocus();
            return;
        }

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
            confirm_password_edit_text.setError("אנא אמת סיסמא");
            confirm_password_edit_text.requestFocus();
            return;
        }

        if(!full_name.contains(" ")) {
            full_name_edit_text.setError("אנא הכנס שם מלא");
            full_name_edit_text.requestFocus();
            return;
        }

        if(phone.length() != 10) {
            phone_edit_text.setError("אנא הכנס מספר טלפון תקין");
            phone_edit_text.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_edit_text.setError("אנא הכנס כתובת מייל חוקית");
            email_edit_text.requestFocus();
            return;
        }

        if(password.length() < 5) {
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

                    User user = new User(full_name, email, phone);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });

                } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    email_edit_text.setError("אימייל זה בשימוש");
                    email_edit_text.requestFocus();
                    return;

                }else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        progress_bar.setVisibility(View.VISIBLE);

        Intent intent = new Intent(this, UserHomePageActivity.class);
        intent.putExtra(EXTRA_FULL_NAME, full_name);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.login_create_account_button:
                registerUser();
                break;

            case R.id.login_already_have_account_button:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}