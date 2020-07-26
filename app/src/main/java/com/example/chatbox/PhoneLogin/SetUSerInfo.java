package com.example.chatbox.PhoneLogin;

    import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

    import android.app.ProgressDialog;
    import android.content.Intent;
import android.os.Bundle;
import android.view.View;
    import android.widget.ProgressBar;
    import android.widget.Toast;

import com.example.chatbox.MainActivity;
import com.example.chatbox.R;
import com.example.chatbox.databinding.ActivitySetUSerInfoBinding;
    import com.example.chatbox.model.User;
    import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SetUSerInfo extends AppCompatActivity {

    private ActivitySetUSerInfoBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_set_u_ser_info);

        initButtonClick();
    }

    private void initButtonClick() {
        binding.ProfileSetupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.UserNameSet.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please enter your Username",Toast.LENGTH_SHORT).show();
                }
                else{
                    dbUpdate();
                }

            }
        });

        binding.circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Still to implement
            }
        });

    }

    private void dbUpdate()
    {

        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firebaseFirestore =  FirebaseFirestore.getInstance();
        if(firebaseUser != null)
        {
            String uId =  firebaseUser.getUid();
            User users = new User(uId,binding.UserNameSet.getText().toString(),firebaseUser.getPhoneNumber(),"","","","","","");
            firebaseFirestore.collection("Users").document(firebaseUser.getUid()).set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"Successfully added user details",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"You need to login into firebase",Toast.LENGTH_SHORT).show();
        }

    }
}