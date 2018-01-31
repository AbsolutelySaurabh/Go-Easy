package com.hackjack.stressrelease.adapter.a.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.absolutelysaurabh.stressrelease.R;
import com.hackjack.stressrelease.activity.gpchatroom.SingleGroupChatRoomActivity;
import com.hackjack.stressrelease.model.Groups;
import com.hackjack.stressrelease.model.Message;

import java.util.ArrayList;

/**
 * Created by absolutelysaurabh on 30/1/18.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {

    public ImageView grp_avatar;
    public TextView sender_name;
    public TextView message;
    public ArrayList<Message> al_messages;
    public Context context;

    public MessageViewHolder(LayoutInflater inflater, final ViewGroup parent, final ArrayList<Message> al_messages, final Context context) {

        super(inflater.inflate(R.layout.item_message, parent, false));
        sender_name = itemView.findViewById(R.id.nameTextView);
        message = itemView.findViewById(R.id.messageTextView);

        this.al_messages = al_messages;
        this.context = context;

    }
}