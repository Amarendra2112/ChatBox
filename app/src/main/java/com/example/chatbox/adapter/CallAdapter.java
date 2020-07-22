package com.example.chatbox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatbox.R;
import com.example.chatbox.model.CallList;

import java.util.List;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.Holder> {

    List<CallList> list;
    Context context;

    public CallAdapter (List<CallList> callLists,Context context)
    {
        this.list = callLists;
        this.context = context;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.call_list_layout,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        CallList callList = list.get(position);

        holder.callerName.setText(callList.getUsername());
        holder.timeOfCall.setText( callList.getDate()+ "  " +callList.getTime());

        if(callList.getCallType().equalsIgnoreCase("receive"))
        {
            holder.callStatus.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_call_received_24));
        }
        else if(callList.getCallType().equalsIgnoreCase("missed"))
        {
            holder.callStatus.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_call_missed_24));
        }
        else
        {
            holder.callStatus.setImageDrawable(context.getDrawable(R.drawable.ic_baseline_call_made_24));
        }

        //Caller profile
        Glide.with(context).load(callList.getUrlProfile()).into(holder.callDp);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView callDp,callStatus;
        TextView callerName,timeOfCall;
        public Holder(@NonNull View itemView) {
            super(itemView);
            callDp = itemView.findViewById(R.id.CallImage);
            callStatus =  itemView.findViewById(R.id.ArrowOfCall);
            callerName = itemView.findViewById(R.id.CallerUserName);
            timeOfCall = itemView.findViewById(R.id.TimeOfCall);
        }
    }
}
