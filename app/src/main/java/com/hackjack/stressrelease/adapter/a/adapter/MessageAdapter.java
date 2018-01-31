package com.hackjack.stressrelease.adapter.a.adapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.absolutelysaurabh.stressrelease.R;
import com.hackjack.stressrelease.adapter.a.viewHolder.GroupsViewHolder;
import com.hackjack.stressrelease.adapter.a.viewHolder.MessageViewHolder;
import com.hackjack.stressrelease.model.Groups;
import com.hackjack.stressrelease.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private Context context;
    private ArrayList<Message> al_message;

    public MessageAdapter(Context context, ArrayList<Message> al_message) {

        this.context = context;
        this.al_message = al_message;
    }

    public MessageAdapter(){

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MessageViewHolder(LayoutInflater.from(parent.getContext()), parent,al_message,context);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {

        holder.sender_name.setText(al_message.get(position).getName());
        holder.message.setText(al_message.get(position).getText());

    }

    @Override
    public int getItemCount() {

        return al_message.size();
    }
}
