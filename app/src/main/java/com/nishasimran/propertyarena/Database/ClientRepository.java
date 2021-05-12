package com.nishasimran.propertyarena.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ClientRepository {

    private final ClientDAO dao;
    private final LiveData<List<Client>> allClients;

    // Note that in order to unit test the AlbumRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ClientRepository(Application application) {
        ProjectRoomDatabase db = ProjectRoomDatabase.getDatabase(application);
        dao = db.clientDAO();
        allClients = dao.getAllClients();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Client>> getAllClients() {
        return allClients;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Client client) {
        ProjectRoomDatabase.databaseWriteExecutor.execute(() -> dao.insert(client));
    }

    public void update(Client client) {
        ProjectRoomDatabase.databaseWriteExecutor.execute(() -> dao.update(client));
    }

    public void delete(Client client) {
        ProjectRoomDatabase.databaseWriteExecutor.execute(() -> dao.delete(client));
    }

    public void deleteAll() {
        ProjectRoomDatabase.databaseWriteExecutor.execute(dao::deleteAll);
    }

    public Client findClient(int id, List<Client> clients) {
        if (clients != null) {
            for (Client client : clients) {
                if (id == client.getId()) {
                    return client;
                }
            }
        }
        return null;
    }
}
