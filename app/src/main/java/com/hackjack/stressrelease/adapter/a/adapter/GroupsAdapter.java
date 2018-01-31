package com.hackjack.stressrelease.adapter.a.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.absolutelysaurabh.stressrelease.R;
import com.hackjack.stressrelease.adapter.a.viewHolder.GroupsViewHolder;
import com.hackjack.stressrelease.model.Groups;

import java.util.ArrayList;

public class GroupsAdapter  extends RecyclerView.Adapter<GroupsViewHolder> {

    private Context context;
    private ArrayList<Groups> al_groups;

    public GroupsAdapter(Context context, ArrayList<Groups> al_groups) {

        this.context = context;
        this.al_groups = al_groups;
    }

    @Override
    public GroupsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

      return new GroupsViewHolder(LayoutInflater.from(parent.getContext()), parent,al_groups,context);
    }

    @Override
    public void onBindViewHolder(GroupsViewHolder holder, int position) {

//        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.error(R.color.greenLight);
//
//        Glide.with(context).load(R.color.greenLight).apply(requestOptions).thumbnail(0.5f).into(holder.grp_avatar);

        holder.grp_name.setText(al_groups.get(position).getGrpName());
        holder.total_members.setText(al_groups.get(position).getGrpTotalMembers());



    }
    @Override
    public int getItemCount() {

        return al_groups.size();
    }
}
