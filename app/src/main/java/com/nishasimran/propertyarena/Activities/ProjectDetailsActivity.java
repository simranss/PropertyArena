package com.nishasimran.propertyarena.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;
import com.nishasimran.propertyarena.Database.Project;
import com.nishasimran.propertyarena.Database.ProjectViewModel;
import com.nishasimran.propertyarena.R;
import com.nishasimran.propertyarena.Values.Values;

public class ProjectDetailsActivity extends AppCompatActivity {

    private final String TAG = "ProDetailsAct";
    private final int DEFAULT_PROJECT_ID = -5;

    private int projectId;

    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        projectId = getIntent().getIntExtra(Values.KEY_PROJECT_ID, DEFAULT_PROJECT_ID);
        if (projectId == DEFAULT_PROJECT_ID) {
            finish();
        }

        initViews();

        project = ProjectViewModel.getInstance(this, getApplication()).findProject(projectId);

        if (project == null) {
            finish();
        }
        Log.d(TAG, "project: " + project);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(project.getProjectName());

    }

    private void initViews() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pro_det_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_edit) {
            // TODO: start editing current Project
            return true;
        } else if (item.getItemId() == R.id.menu_delete) {
            // TODO: delete the current project
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}