package com.example.chatbox.PhoneLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbox.MainActivity;
import com.example.chatbox.R;
import com.example.chatbox.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class PhoneAuth extends AppCompatActivity implements View.OnClickListener {

    private TextView heading,code;
    private Button verify;
    private EditText countryCode,phnNumb;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser,fireUser;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private String mVerificationCode;
    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        userIsLogin();


        heading = findViewById(R.id.VerificationText);
        verify = findViewById(R.id.SendCode);
        verify.setText("send");
        countryCode = findViewById(R.id.CountryCode);
        phnNumb = findViewById(R.id.PhoneNumberUser);
        code = findViewById(R.id.VerifyCode);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        verify.setOnClickListener(this);

        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
                Log.d("Complete","It is getting completed");
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getApplicationContext(),"Verification failed",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerificationCode = s;
            }
        };
    }

    @Override
    public void onClick(View v) {
        if(verify.getText().toString() == "send")
        {
            if(countryCode.getText().toString().length() != 3 && phnNumb.getText().toString().length() !=10)
            {
                Toast.makeText(this,"Please enter valid country code or number",Toast.LENGTH_SHORT).show();
            }

            else
            {
                progressDialog.setMessage("Processing..");
                progressDialog.show();
                String phone = countryCode.getText().toString() + phnNumb.getText().toString();
                Log.d("Phone",phone);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phone,        // Phone number to verify
                        120,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        this,               // Activity (for callback binding)
                        mCallBacks);        // OnVerificationStateChangedCallbacks


                verify.setText("verify");
            }

        }
        else
        {
            verifyPhoneNumberWithCode(mVerificationCode,code.getText().toString());
        }
    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user != null)
                            {
                                String uId =  user.getUid();
                                User users = new User(uId,"",user.getPhoneNumber(),"","","","","","");
                                firebaseFirestore.collection("Users").add(users).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        userIsLogin();
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }
                        }
                    }
                });
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    public void userIsLogin()
    {
        progressDialog.dismiss();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null)
        {
            Intent intent = new Intent(this,SetUSerInfo.class);
            startActivity(intent);
            finish();
        }

    }

}