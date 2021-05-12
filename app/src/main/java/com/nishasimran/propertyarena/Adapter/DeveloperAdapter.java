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

import org.w3c.dom.Text;

import java.util.List;

public class DeveloperAdapter extends RecyclerView.Adapter<DeveloperAdapter.DeveloperViewHolder> {

    List<Project> projects;
    Context context;

    public DeveloperAdapter(Context context, List<Project> projects) {
        this.context = context;
        this.projects = projects;
    }


    @NonNull
    @Override
    public DeveloperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.developer_list_item, parent, false);
        return new DeveloperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeveloperViewHolder holder, int position) {
        holder.developerTextView.setText(projects.get(position).getDeveloperName());
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    static class DeveloperViewHolder extends RecyclerView.ViewHolder {

        TextView developerTextView;

        public DeveloperViewHolder(@NonNull View itemView) {
            super(itemView);
            developerTextView = itemView.findViewById(R.id.textView);
        }
    }
}
