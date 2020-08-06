package com.example.chatbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.chatbox.R;
import com.example.chatbox.model.Chat.Chats;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.Holder> {

    List<Chats> list;
    private Context context;
    private static final int MAG_TYPE_LEFT = 0;
    private static final int MAG_TYPE_RIGHT = 1;
    private FirebaseUser firebaseUser;

    public ChatAdapter(List<Chats> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MAG_TYPE_LEFT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new Holder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new Holder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private TextView message;
        public Holder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);

        }
        void bind(Chats chats){
            message.setText(chats.getTextMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(list.get(position).getSender().equals(firebaseUser.getUid()))
        {
            return MAG_TYPE_RIGHT;
        }
        else
        {
            return MAG_TYPE_LEFT;
        }
    }
}
