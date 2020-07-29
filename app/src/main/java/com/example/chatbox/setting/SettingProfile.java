package com.example.chatbox.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.TextView;

import com.example.chatbox.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SettingProfile extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    TextView userName,phone,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);

        userName = findViewById(R.id.ProfileAboutStatus);
        phone = findViewById(R.id.ProfilePhoneDetail);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        if(firebaseUser != null)
        {
            populate();
        }
    }

    private void populate() {
        firebaseFirestore.collection("Users").document(firebaseUser.getUid().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String username = Objects.requireNonNull(documentSnapshot.get("userName")).toString();
                userName.setText(username);
                String userPhone = Objects.requireNonNull(documentSnapshot.get("userPhone")).toString();
                phone.setText(userPhone);
            }
        });
    }
}