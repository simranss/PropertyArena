package com.nishasimran.propertyarena.Database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.util.List;

public class ClientViewModel extends AndroidViewModel {


    private final String TAG = "ClientVM";
    private static ClientViewModel INSTANCE = null;

    private final ClientRepository repository;

    private final LiveData<List<Client>> allClients;

    public ClientViewModel(@NonNull Application application) {
        super(application);
        repository = new ClientRepository(application);
        allClients = repository.getAllClients();
    }

    public static ClientViewModel getInstance(@NonNull ViewModelStoreOwner owner, @NonNull Application application) {
        if (INSTANCE == null) {
            ViewModelProvider.AndroidViewModelFactory factory = new ViewModelProvider.AndroidViewModelFactory(application);
            INSTANCE = new ViewModelProvider(owner, factory).get(ClientViewModel.class);
        }
        return INSTANCE;
    }

    public LiveData<List<Client>> getAllClients() { return allClients; }

    public void insert(Client client) { repository.insert(client); }

    public void update(Client client) { repository.update(client); }

    public void delete(Client client) { repository.delete(client); }

    public void deleteAll() { repository.deleteAll(); }

    public Client findClient(int clientId, List<Client> clients) {
        return repository.findClient(clientId, clients);
    }
}
