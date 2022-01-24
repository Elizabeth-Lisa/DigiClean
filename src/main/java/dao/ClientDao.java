package dao;

import models.Client;

import java.util.List;

public interface ClientDao {
    //List all clients
    List<Client> getAllCleints();

    //create an new client
    void addClient(Client client);

    //get a specific Client
    Client findClientById(int id);

    Client findClientByIdAndPassword(int clientIdNo, int clientPassword);


        //Update a Client


    //Delete a client
    void deleteClientById(int id);

    //Delete all clients
    void deleteAllClients();
}
