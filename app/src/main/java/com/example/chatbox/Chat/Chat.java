package com.example.chatbox.Chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.chatbox.R;
import com.example.chatbox.databinding.ActivityChatBinding;

public class Chat extends AppCompatActivity {

    private ActivityChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        String userId = intent.getStringExtra("userId");
        String userProfile = intent.getStringExtra("userProfile");

        if(userName != null)
        {
            binding.ChatUserName.setText(userName);
            Glide.with(this).load(userProfile).into(binding.ChatUserProfile);
        }
        binding.ChatBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}