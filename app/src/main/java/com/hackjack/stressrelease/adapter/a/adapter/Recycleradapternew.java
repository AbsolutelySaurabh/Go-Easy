package com.hackjack.stressrelease.adapter.a.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.absolutelysaurabh.stressrelease.R;
import com.hackjack.stressrelease.adapter.a.viewHolder.chat_rec;
import com.hackjack.stressrelease.model.ChatMessage;

import java.util.List;

/**
 * Created by Sunain on 12/31/2017.
 */

public class Recycleradapternew extends RecyclerView.Adapter<chat_rec> {
    private List<ChatMessage> chatlist;

    public Recycleradapternew(List<ChatMessage> chatlist) {
        this.chatlist = chatlist;
    }
    @Override
    public chat_rec onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.msglist, parent, false);

        return new chat_rec(itemView);
    }

    @Override
    public void onBindViewHolder(chat_rec holder, int position) {
        ChatMessage chat = chatlist.get(position);
        if(chat.getMsgUser().equals("user"))
        {
            holder.rightText.setText(chat.getMsgText());
            holder.rightText.setVisibility(View.VISIBLE);
            holder.leftText.setVisibility(View.GONE);
        }
        if(chat.getMsgUser().equals("bot"))
        {
            holder.leftText.setText(chat.getMsgText());
            holder.rightText.setVisibility(View.GONE);
            holder.leftText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }
}
