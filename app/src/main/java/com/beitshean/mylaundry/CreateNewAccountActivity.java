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

public class CreateNewAccountActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText email_edit_text, password_edit_text;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        email_edit_text = (EditText) findViewById(R.id.login_email_edit_text);
        password_edit_text = (EditText) findViewById(R.id.login_password_edit_text);
        progress_bar = (ProgressBar) findViewById(R.id.login_progress_bar);

        findViewById(R.id.login_create_new_account_button).setOnClickListener(this);
        findViewById(R.id.login_connect_button).setOnClickListener(this);

        progress_bar.setVisibility(View.INVISIBLE);
    }

    private void userLogin() {

        String email = email_edit_text.getText().toString().trim();
        String password = password_edit_text.getText().toString().trim();

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

        progress_bar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress_bar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Intent intent = new Intent(CreateNewAccountActivity.this, UserHomePageActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_create_new_account_button:
                startActivity(new Intent(this, CreateNewAccountActivity.class));
                break;

            case R.id.login_connect_button:
                userLogin();
                break;
        }
    }
}
