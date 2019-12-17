package com.beitshean.mylaundry;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;



public class ViewDatabase extends AppCompatActivity {
    private static final String TAG = "ViewDatabase";

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    public String full_name, email, phone;
    public boolean is_manager;

    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }

            private void showData(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User uInfo = new User();
                    uInfo.setFullName(ds.child(userID).getValue(User.class).getFullName());
                    uInfo.setEmail(ds.child(userID).getValue(User.class).getEmail());
                    uInfo.setPhone(ds.child(userID).getValue(User.class).getPhone());
                    uInfo.setManager(ds.child(userID).getValue(User.class).getIsManager());

                    full_name = uInfo.getFullName();
                    email = uInfo.getEmail();
                    phone = uInfo.getPhone();
                    is_manager = uInfo.getIsManager();


                    ArrayList<String> array = new ArrayList<>();
                    array.add(full_name);
                    array.add(email);
                    array.add(phone);
                    // ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.,array);
                    //mListView.setAdapter(adapter);
                }
            }

        };
    }
}