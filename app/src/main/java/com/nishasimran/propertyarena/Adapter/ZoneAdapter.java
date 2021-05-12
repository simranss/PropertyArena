package com.nishasimran.propertyarena.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nishasimran.propertyarena.Database.Project;
import com.nishasimran.propertyarena.R;

import java.util.List;

public class ZoneAdapter extends RecyclerView.Adapter<ZoneAdapter.ZoneViewHolder> {

    List<Project> projects;
    Context context;

    public ZoneAdapter(Context context, List<Project> projects) {
        this.context = context;
        this.projects = projects;
    }

    @NonNull
    @Override
    public ZoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_list_item, parent, false);
        return new ZoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZoneViewHolder holder, int position) {
        holder.projectTextView.setText(projects.get(position).getProjectName());
        String carpet = projects.get(position).getCarpet() + "sq. ft.";
        holder.carpetTextView.setText(carpet);
        holder.statusTextView.setText(projects.get(position).getStatus());
        holder.mainHeadTextView.setText(R.string.zone_head);
        holder.mainTextView.setText(projects.get(position).getZone());
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    static class ZoneViewHolder extends RecyclerView.ViewHolder {

        TextView mainTextView, projectTextView, statusTextView, carpetTextView, mainHeadTextView;

        public ZoneViewHolder(@NonNull View itemView) {
            super(itemView);
            mainTextView = itemView.findViewById(R.id.dev_list_main);
            mainHeadTextView = itemView.findViewById(R.id.dev_list_main_head);
            projectTextView = itemView.findViewById(R.id.dev_list_project_name);
            statusTextView = itemView.findViewById(R.id.dev_list_status);
            carpetTextView = itemView.findViewById(R.id.dev_list_carpet);
        }
    }
}