package com.nishasimran.propertyarena.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nishasimran.propertyarena.Database.Client;
import com.nishasimran.propertyarena.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ClientsViewHolder> {

    List<Client> clients;
    Context context;

    public ClientsAdapter(Context context, List<Client> clients) {
        this.context = context;
        this.clients = clients;
    }

    @NonNull
    @NotNull
    @Override
    public ClientsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.developer_list_item, parent, false);
        return new ClientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ClientsViewHolder holder, int position) {
        holder.developerTextView.setText(clients.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    static class ClientsViewHolder extends RecyclerView.ViewHolder {

        TextView developerTextView;

        public ClientsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            developerTextView = itemView.findViewById(R.id.textView);
        }
    }
}
