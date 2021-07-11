package com.nishasimran.propertyarena.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.nishasimran.propertyarena.Values.Values;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Project.class, Client.class}, version = 1, exportSchema = false)
public abstract class ProjectRoomDatabase extends RoomDatabase {

    public abstract ProjectDAO projectDAO();
    public abstract ClientDAO clientDAO();
    
    private static volatile ProjectRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ProjectRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProjectRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ProjectRoomDatabase.class,
                            Values.DATABASE_PROJECTS
                    )
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                ProjectDAO projectDao = INSTANCE.projectDAO();
                ClientDAO clientDao = INSTANCE.clientDAO();
            });
        }
    };
}
