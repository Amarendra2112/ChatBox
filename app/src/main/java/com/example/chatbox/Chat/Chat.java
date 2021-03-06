package com.example.chatbox.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chatbox.R;
import com.example.chatbox.adapter.ChatAdapter;
import com.example.chatbox.databinding.ActivityChatBinding;
import com.example.chatbox.menu.Notification;
import com.example.chatbox.model.Chat.Chats;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Chat extends AppCompatActivity {

    private ActivityChatBinding binding;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private String receiverId;
    private ChatAdapter chatAdapter;
    private List<Chats> list;
    private String lastMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chat);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

        ScrollView scrollView= new ScrollView(this);
        scrollView.isEnabled();
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        receiverId = intent.getStringExtra("userId");
        String userProfile = intent.getStringExtra("userProfile");

        if(userName != null)
        {
            binding.ChatUserName.setText(userName);
            if(!userProfile.isEmpty() && userProfile != "")
            {
                Glide.with(this).load(userProfile).into(binding.ChatUserProfile);
            }

        }
        binding.ChatBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.ChatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.ChatMessage.getText().toString().isEmpty())
                {
                    Log.d("Button","Send button works");

                    Date date = Calendar.getInstance().getTime();
                    @SuppressLint("SimpleDateFormat")SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:SS");
                    String today = format.format(date);


                    Chats chats = new   Chats(today,binding.ChatMessage.getText().toString(),"Text",firebaseUser.getUid(),receiverId);

                    reference.child("Chats").push().setValue(chats).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                    //Add to chat list

                    DatabaseReference chatRef1 = FirebaseDatabase.getInstance().getReference("chatList").child(firebaseUser.getUid()).child(receiverId);
                    chatRef1.child("chatId").setValue(receiverId);

                    DatabaseReference chatRef2 = FirebaseDatabase.getInstance().getReference("chatList").child(receiverId).child(firebaseUser.getUid());
                    chatRef2.child("chatId").setValue(firebaseUser.getUid());

                    lastMessage = binding.ChatMessage.getText().toString();
                    Notification notification = new Notification();
                    notification.setMsg(lastMessage);
                    DatabaseReference chatNotification1 = FirebaseDatabase.getInstance().getReference("LastChat").child(firebaseUser.getUid()).child(receiverId);
                    chatNotification1.child("LastMessageSent").setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Notification","Working");
                        }
                    });

                    binding.ChatMessage.setText("");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No message to send",Toast.LENGTH_SHORT).show();
                }
            }
        });

        list = new ArrayList<>();

        binding.ChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        readChat();

    }

    private void readChat() {
        try
        {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            reference.child("Chats").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for(DataSnapshot snapshot1 : snapshot.getChildren())
                    {

                        Chats chats = snapshot1.getValue(Chats.class);
                        if(chats.getSender().equals(firebaseUser.getUid()) && chats.getReceiver().equals(receiverId) || chats.getSender().equals(receiverId) && chats.getReceiver().equals(firebaseUser.getUid()))
                        {
                            list.add(chats);
                        }
                    }

                    Chats ch = list.get(list.size()-1);
                    String msg = ch.getTextMessage();
                    Notification notification = new Notification();
                    notification.setMsg(msg);
                    DatabaseReference chatNotification2 = FirebaseDatabase.getInstance().getReference("LastChat").child(firebaseUser.getUid()).child(receiverId);
                    chatNotification2.child("LastMessageSeen").setValue(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Notification","Working");
                        }
                    });


                    if(chatAdapter != null)
                    {
                        binding.ChatRecyclerView.smoothScrollToPosition(list.size()-1);
                        chatAdapter.notifyDataSetChanged();
                    }
                    else
                    {

                        chatAdapter = new ChatAdapter(list,Chat.this);
                        binding.ChatRecyclerView.setAdapter(chatAdapter);
                        binding.ChatRecyclerView.smoothScrollToPosition(list.size()-1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }


}