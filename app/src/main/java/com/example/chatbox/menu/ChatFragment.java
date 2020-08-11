package com.example.chatbox.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.chatbox.R;
import com.example.chatbox.adapter.Adapter;
import com.example.chatbox.model.ChatList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private RecyclerView recyclerView;
    private ConstraintLayout noText;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private List<ChatList> list;
    private List<String> allUser;
    private FirebaseFirestore firebaseFirestore;
    private Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.RecyclerViewChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ScrollView scrollView = new ScrollView(getContext());
        scrollView.isEnabled();


        noText = view.findViewById(R.id.NoFriendConstrainedLayout);
        list = new ArrayList<>();
        allUser = new ArrayList<>();



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore =  FirebaseFirestore.getInstance();

        getChatList();




        return view;
    }

    private void getChatList() {
        list.clear();
        allUser.clear();
        reference.child("chatList").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    String userId = Objects.requireNonNull(snapshot1.child("chatId").getValue().toString());
                    allUser.add(userId);
                }
                getUserData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUserData() {

        for(final String user : allUser)
        {
            firebaseFirestore.collection("Users").document(user).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    ChatList chatList = new ChatList();
                    chatList.setUserId(documentSnapshot.get("userId").toString());
                    chatList.setUserName(documentSnapshot.get("userName").toString());
                    chatList.setUrlProfile(documentSnapshot.get("imageProfile").toString());

                    list.add(chatList);

                    if(adapter != null)
                    {
                        adapter.notifyItemInserted(0);
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        if(list.isEmpty())
                        {
                            noText.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            noText.setVisibility(View.GONE);
                            adapter = new Adapter(getContext(),list);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

    }

}