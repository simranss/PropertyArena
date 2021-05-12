package com.nishasimran.propertyarena.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nishasimran.propertyarena.Adapter.DeveloperAdapter;
import com.nishasimran.propertyarena.Database.Project;
import com.nishasimran.propertyarena.Database.ProjectRepository;
import com.nishasimran.propertyarena.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DeveloperFragment extends Fragment {

    private final String TAG = "DevelopFrag";

    private RecyclerView recyclerView;
    private List<Project> projects;
    private DeveloperAdapter adapter;
    AppCompatActivity activity;

    public DeveloperFragment(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_developer, container, false);

        initViews(view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);

        projects = new ArrayList<>();
        adapter = new DeveloperAdapter(getContext(), projects);

        recyclerView.setAdapter(adapter);

        ProjectRepository.getInstance(activity.getApplication()).getAllAlbums().observe(activity, projects -> {
            Log.d(TAG, "projects changed: " + projects);
            DeveloperFragment.this.projects.clear();
            DeveloperFragment.this.projects.addAll(projects);
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    private void initViews(View parent) {
        recyclerView = parent.findViewById(R.id.developer_recycler);
    }
}