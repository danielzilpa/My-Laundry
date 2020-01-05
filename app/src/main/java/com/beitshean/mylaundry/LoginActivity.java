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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_FULL_NAME = "com.beitshean.mylaundry.EXTRA_FULL_NAME";

    FirebaseAuth mAuth;
    EditText email_edit_text, password_edit_text;
    ProgressBar progress_bar;
    DatabaseReference reff;
    String user_full_name, user_email, user_phone, user_is_manager;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        email_edit_text = findViewById(R.id.login_email_edit_text);
        password_edit_text = findViewById(R.id.login_password_edit_text);
        progress_bar = findViewById(R.id.login_progress_bar);

        findViewById(R.id.login_create_account_button).setOnClickListener(this);

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

                    uid = mAuth.getUid();
                    reff = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                    reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            user_full_name = dataSnapshot.child("fullName").getValue().toString();
                            user_is_manager = dataSnapshot.child("isManager").getValue().toString();

                            if(user_is_manager.equals("true")) {
                                Intent intent = new Intent(LoginActivity.this, ManagerHomePageActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra(EXTRA_FULL_NAME, user_full_name);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(LoginActivity.this, UserHomePageActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra(EXTRA_FULL_NAME, user_full_name);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.login_create_account_button:
                userLogin();
                break;
        }
    }
}