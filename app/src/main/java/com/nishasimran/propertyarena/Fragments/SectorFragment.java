package com.nishasimran.propertyarena.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nishasimran.propertyarena.Activities.MainActivity;
import com.nishasimran.propertyarena.Adapter.SectorAdapter;
import com.nishasimran.propertyarena.Adapter.SectorAdapter;
import com.nishasimran.propertyarena.Database.Project;
import com.nishasimran.propertyarena.Database.ProjectViewModel;
import com.nishasimran.propertyarena.R;

import java.util.ArrayList;
import java.util.List;

public class SectorFragment extends Fragment {

    private final String TAG = "SectorFrag";

    private RecyclerView recyclerView;
    private List<Project> projects;
    private SectorAdapter adapter;
    MainActivity activity;

    public SectorFragment(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_sector, container, false);

        initViews(parent);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        projects = new ArrayList<>();
        adapter = new SectorAdapter(getContext(), projects);

        recyclerView.setAdapter(adapter);

        ProjectViewModel.getInstance(activity, activity.getApplication()).getAllProjects().observe(activity, projects -> {
            Log.d(TAG, "projects changed: " + projects);
            SectorFragment.this.projects.clear();
            SectorFragment.this.projects.addAll(projects);
            adapter.notifyDataSetChanged();
        });
        
        return parent;
    }

    private void initViews(View parent) {
        recyclerView = parent.findViewById(R.id.sector_recycler);
    }
}