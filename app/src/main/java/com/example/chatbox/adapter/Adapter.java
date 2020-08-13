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
import com.example.chatbox.model.ChatList;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    private List<ChatList> list;
    private Context context;

    public Adapter(Context context,List<ChatList> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_circular_list,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        final ChatList chatList = list.get(position);

        holder.user.setText(chatList.getUserName());
        //holder.date.setText(chatList.getDate());
        holder.description.setText(chatList.getDescription());

        //for profile
        if(!chatList.getUrlProfile().isEmpty())
        {
            Glide.with(context).load(chatList.getUrlProfile()).into(holder.profile);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Chat.class).putExtra("userId",chatList.getUserId()).putExtra("userName",chatList.getUserName()).putExtra("userProfile",chatList.getUrlProfile()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView user,date,description;
        private CircularImageView profile;

        public Holder(@NonNull View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.UserID);
            //date = itemView.findViewById(R.id.TimeStamp);
            description = itemView.findViewById(R.id.DescriptionOfChat);
            profile = itemView.findViewById(R.id.DpImage);
        }
    }
}
