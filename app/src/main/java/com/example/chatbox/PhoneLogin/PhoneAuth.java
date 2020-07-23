package com.example.chatbox.PhoneLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chatbox.MainActivity;
import com.example.chatbox.R;

public class PhoneAuth extends AppCompatActivity implements View.OnClickListener {

    private TextView heading;
    private Button verify;
    private EditText countryCode,phnNumb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        heading = findViewById(R.id.VerificationText);
        verify = findViewById(R.id.SendCode);
        countryCode = findViewById(R.id.CountryCode);
        phnNumb = findViewById(R.id.PhoneNumberUser);
        verify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}