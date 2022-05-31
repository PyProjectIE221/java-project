package com.example.finalproject.Chat;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<ChatObject> chatObjectList;
    private Context mContext;
    public ChatAdapter(List<ChatObject> chatObjectList, Context mContext){
        this.chatObjectList = chatObjectList;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatObject obj = chatObjectList.get(position);
        holder.mMessage.setText(obj.getMessage());
        if(obj.getCurrentUser()){
            holder.mContainer.setGravity(Gravity.END);
            holder.mMessage.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mMessage.setBackground(ContextCompat.getDrawable(mContext, R.drawable.first_chat));
        }else{
            holder.mContainer.setGravity(Gravity.START);
            holder.mMessage.setTextColor(Color.parseColor("#000000"));
            holder.mMessage.setBackground(ContextCompat.getDrawable(mContext, R.drawable.second_chat));
        }
    }

    @Override
    public int getItemCount() {
        return chatObjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mMessage;
        public LinearLayout mContainer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mMessage = itemView.findViewById(R.id.message);
            mContainer = itemView.findViewById(R.id.container);
        }
    }
}
