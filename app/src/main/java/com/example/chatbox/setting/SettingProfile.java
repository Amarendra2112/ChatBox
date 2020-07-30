package com.example.chatbox.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatbox.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SettingProfile extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private FirebaseFirestore firebaseFirestore;
    TextView userName,phone;
    ImageView camera;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_profile);

        userName = findViewById(R.id.ProfileAboutStatus);
        phone = findViewById(R.id.ProfilePhoneDetail);
        camera = findViewById(R.id.ProfilePicEdit);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        if(firebaseUser != null)
        {
            populate();
        }
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetLayout();
            }
        });
    }

    private void showBottomSheetLayout() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_dialog_box,null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomSheetDialog = null;
            }
        });
        bottomSheetDialog.show();
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