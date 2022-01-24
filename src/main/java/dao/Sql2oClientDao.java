package dao;

import models.Client;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oClientDao implements ClientDao{
    private final Sql2o sql2o;

    public Sql2oClientDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    public void getDrivers(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Client> getAllCleints() {
        getDrivers();
        try(Connection con = sql2o.open()){
            return  con.createQuery("SELECT * FROM clients")
                    .executeAndFetch(Client.class);
        }
    }

    @Override
    public void addClient(Client client) {
        getDrivers();
        String sql = "INSERT INTO clients (clientName,clientPassword,clientIdNo, clientPhone,clientLocation) VALUES (:clientName, :clientPassword, :clientIdNo, :clientPhone, :clientLocation)";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql, true)
                    .bind(client)
                    .executeUpdate()
                    .getKey();
            client.setId(id);
        }catch (Sql2oException e){
            e.printStackTrace();
        }
    }

    @Override
    public Client findClientById(int id) {
        getDrivers();
        String sql = "SELECT * FROM client WHERE id = :id";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Client.class);
        }

    }

    @Override
    public void deleteClientById(int id) {
        getDrivers();
        String sql = "DELETE FROM clients WHERE id = :id";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }catch (Sql2oException e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteAllClients() {
        getDrivers();

        String sql = "DELETE from clients";

        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .executeUpdate();
        }catch (Sql2oException e){
            e.printStackTrace();
        }

    }
}
