package com.hackjack.stressrelease.adapter.a.viewHolder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.absolutelysaurabh.stressrelease.R;

/**
 * Created by beast on 14/4/17.
 */

public class chat_rec extends RecyclerView.ViewHolder  {

    public TextView leftText,rightText;

    public chat_rec(View itemView) {
        super(itemView);

        leftText = (TextView) itemView.findViewById(R.id.leftText);
        rightText = (TextView) itemView.findViewById(R.id.rightText);

    }
}
