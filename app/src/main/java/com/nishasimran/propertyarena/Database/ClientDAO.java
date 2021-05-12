package com.nishasimran.propertyarena.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClientDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Client client);

    @Update
    void update(Client client);

    @Delete
    void delete(Client client);

    @Query("DELETE FROM " + Values.TABLE_CLIENTS)
    void deleteAll();

    @Query("SELECT * FROM " + Values.TABLE_CLIENTS + " ORDER BY " + Values.COLUMN_CLIENT_NAME + " ASC")
    LiveData<List<Client>> getAllClients();
}
