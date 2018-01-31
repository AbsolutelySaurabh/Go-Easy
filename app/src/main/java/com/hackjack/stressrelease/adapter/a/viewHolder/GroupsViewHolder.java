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

import java.util.ArrayList;

/**
 * Created by absolutelysaurabh on 30/1/18.
 */
public class GroupsViewHolder extends RecyclerView.ViewHolder {

    public ImageView grp_avatar;
    public TextView grp_name;
    public TextView total_members;
    public ArrayList<Groups> al_groups;
    public Context context;

    public GroupsViewHolder(LayoutInflater inflater, final ViewGroup parent, final ArrayList<Groups> al_groups, final Context context) {

        super(inflater.inflate(R.layout.groups_single_card, parent, false));
        grp_avatar = itemView.findViewById(R.id.grp_avatar);
        total_members = itemView.findViewById(R.id.grp_total_members);
        grp_name = itemView.findViewById(R.id.grp_name);

        this.al_groups = al_groups;
        this.context = context;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intent = new Intent(context, SingleGroupChatRoomActivity.class);
                intent.putExtra("GROUP_ID", al_groups.get(getAdapterPosition()).getGpId());
                intent.putExtra("GROUP_NAME", al_groups.get(getAdapterPosition()).getGrpName());
                context.startActivity(intent);

            }
        });
    }
}
