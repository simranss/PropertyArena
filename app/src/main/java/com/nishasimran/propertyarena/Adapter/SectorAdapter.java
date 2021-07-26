package com.nishasimran.propertyarena.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.nishasimran.propertyarena.Activities.ProjectDetailsActivity;
import com.nishasimran.propertyarena.Database.Project;
import com.nishasimran.propertyarena.R;
import com.nishasimran.propertyarena.Values.Values;

import java.util.List;

public class SectorAdapter extends RecyclerView.Adapter<SectorAdapter.SectorViewHolder> {

    List<Project> projects;
    Context context;

    public SectorAdapter(Context context, List<Project> projects) {
        this.context = context;
        this.projects = projects;
    }

    @NonNull
    @Override
    public SectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_list_item, parent, false);
        return new SectorViewHolder(context, this, view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectorViewHolder holder, int position) {
        holder.projectTextView.setText(projects.get(position).getProjectName());
        String rate = "₹" + projects.get(position).getRate();
        holder.rateTextView.setText(rate);
        holder.statusTextView.setText(projects.get(position).getStatus());
        holder.mainTextView.setText(projects.get(position).getSector());
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    static class SectorViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView parent;
        TextView mainTextView, projectTextView, statusTextView, rateTextView, mainHeadTextView;

        public SectorViewHolder(Context context, SectorAdapter adapter, @NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.pro_list_item);
            mainTextView = itemView.findViewById(R.id.pro_list_main);
            mainHeadTextView = itemView.findViewById(R.id.pro_list_main_head);
            projectTextView = itemView.findViewById(R.id.pro_list_project_name);
            statusTextView = itemView.findViewById(R.id.pro_list_status);
            rateTextView = itemView.findViewById(R.id.pro_list_rate);

            mainHeadTextView.setText(R.string.sector_head);

            parent.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProjectDetailsActivity.class);
                intent.putExtra(Values.KEY_PROJECT_ID, adapter.projects.get(getAdapterPosition()).getId());
                context.startActivity(intent);
            });
        }
    }
}
