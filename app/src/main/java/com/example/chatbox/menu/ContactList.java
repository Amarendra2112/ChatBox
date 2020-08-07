package com.example.chatbox.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.chatbox.R;
import com.example.chatbox.adapter.ContactListAdapter;
import com.example.chatbox.databinding.ActivityContactListBinding;
import com.example.chatbox.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class ContactList extends AppCompatActivity {

    private ActivityContactListBinding binding;
    private List<User> list = new ArrayList<>();
    private ContactListAdapter adapter;
    private FirebaseUser user;
    private FirebaseFirestore firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_contact_list);

        binding.ContactListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseStorage = FirebaseFirestore.getInstance();
        if(user != null)
        {
            getContactList();
        }
        binding.ContactMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getContactList() {
        firebaseStorage.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for(QueryDocumentSnapshot snapshots:queryDocumentSnapshots)
                {
                    String userName = snapshots.getString("userName");
                    String status = snapshots.getString("bio");
                    String userId = snapshots.getString("userId");
                    String imageUrl = snapshots.getString("imageProfile");
                    if(userName != "")
                    {
                        if(!userId.equals(user.getUid()))
                        {
                            User user = new User(userId,userName,"",imageUrl,"","","","",status);
                            list.add(user);
                        }
                    }

                }
                if(list.isEmpty())
                {
                    binding.NoFriendConstrainedLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    binding.NoFriendConstrainedLayout.setVisibility(View.GONE);
                    adapter = new ContactListAdapter(list,ContactList.this);
                    binding.ContactListRecyclerView.setAdapter(adapter);
                }

            }
        });
    }
}