package com.nishasimran.propertyarena.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.nishasimran.propertyarena.Values.Values;

import java.util.List;

@Dao
public interface ProjectDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Project project);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);

    @Query("DELETE FROM " + Values.TABLE_PROJECTS)
    void deleteAll();

    @Query("SELECT * FROM " + Values.TABLE_PROJECTS + " ORDER BY " + Values.COLUMN_PROJECT_NAME + " ASC")
    LiveData<List<Project>> getAllProjects();
}
