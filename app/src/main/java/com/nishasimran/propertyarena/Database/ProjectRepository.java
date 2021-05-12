package com.nishasimran.propertyarena.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ProjectRepository {

    private final ProjectDAO dao;
    private final LiveData<List<Project>> allProjects;

    // Note that in order to unit test the AlbumRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ProjectRepository(Application application) {
        ProjectRoomDatabase db = ProjectRoomDatabase.getDatabase(application);
        dao = db.projectDAO();
        allProjects = dao.getAllProjects();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Project project) {
        ProjectRoomDatabase.databaseWriteExecutor.execute(() -> dao.insert(project));
    }

    public void update(Project project) {
        ProjectRoomDatabase.databaseWriteExecutor.execute(() -> dao.update(project));
    }

    public void delete(Project project) {
        ProjectRoomDatabase.databaseWriteExecutor.execute(() -> dao.delete(project));
    }

    public void deleteAll() {
        ProjectRoomDatabase.databaseWriteExecutor.execute(dao::deleteAll);
    }

    public Project findProject(int id, List<Project> projects) {
        if (projects != null) {
            for (Project project : projects) {
                if (id == project.getId()) {
                    return project;
                }
            }
        }
        return null;
    }
}
