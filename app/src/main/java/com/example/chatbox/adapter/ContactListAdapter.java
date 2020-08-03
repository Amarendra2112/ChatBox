package com.example.chatbox.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatbox.Chat.Chat;
import com.example.chatbox.R;
import com.example.chatbox.model.User;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.Holder> {

    private List<User> list;
    private Context context;

    public ContactListAdapter(List<User> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.usercontact,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final User user = list.get(position);

        holder.userName.setText(user.getUserName());
        holder.status.setText(user.getBio());
        Glide.with(context).load(user.getImageProfile()).into(holder.profile);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Chat.class).putExtra("userId",user.getUserId()).putExtra("userName",user.getUserName()).putExtra("userProfile",user.getImageProfile()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private CircularImageView profile;
        private TextView userName,status;
        public Holder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.ContactBlockUSerProfilePic);
            userName = itemView.findViewById(R.id.ContactBlockUserName);
            status = itemView.findViewById(R.id.ContactBlockStatus);
        }
    }
}
