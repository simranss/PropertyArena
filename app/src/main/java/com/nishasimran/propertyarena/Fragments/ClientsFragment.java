package com.nishasimran.propertyarena.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nishasimran.propertyarena.Adapter.ClientsAdapter;
import com.nishasimran.propertyarena.Database.Client;
import com.nishasimran.propertyarena.Database.ClientViewModel;
import com.nishasimran.propertyarena.R;

import java.util.ArrayList;
import java.util.List;

public class ClientsFragment extends Fragment {

    private final String TAG = "ClientsFrag";

    private RecyclerView recyclerView;
    private List<Client> clients;
    private ClientsAdapter adapter;
    AppCompatActivity activity;

    public ClientsFragment(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clients, container, false);

        initViews(view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        clients = new ArrayList<>();
        adapter = new ClientsAdapter(getContext(), clients);

        recyclerView.setAdapter(adapter);

        ClientViewModel.getInstance(activity, activity.getApplication()).getAllClients().observe(activity, projects -> {
            Log.d(TAG, "clients changed: " + projects);
            ClientsFragment.this.clients.clear();
            ClientsFragment.this.clients.addAll(projects);
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    private void initViews(View parent) {
        recyclerView = parent.findViewById(R.id.clients_recycler);
    }
}