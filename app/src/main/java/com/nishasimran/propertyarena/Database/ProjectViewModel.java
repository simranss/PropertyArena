package com.nishasimran.propertyarena.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {


    private final String TAG = "ProjectVM";
    private static ProjectViewModel INSTANCE = null;

    private final ProjectRepository repository;

    private final LiveData<List<Project>> allProjects;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        repository = new ProjectRepository(application);
        allProjects = repository.getAllProjects();
    }

    public static ProjectViewModel getInstance(@NonNull ViewModelStoreOwner owner, @NonNull Application application) {
        if (INSTANCE == null) {
            ViewModelProvider.AndroidViewModelFactory factory = new ViewModelProvider.AndroidViewModelFactory(application);
            INSTANCE = new ViewModelProvider(owner, factory).get(ProjectViewModel.class);
        }
        return INSTANCE;
    }

    public LiveData<List<Project>> getAllProjects() { return allProjects; }

    public void insert(Project project) { repository.insert(project); }

    public void update(Project project) { repository.update(project); }

    public void delete(Project project) { repository.delete(project); }

    public void deleteAll() { repository.deleteAll(); }

    public Project findProject(int projectId, List<Project> projects) {
        return repository.findProject(projectId, projects);
    }
}
