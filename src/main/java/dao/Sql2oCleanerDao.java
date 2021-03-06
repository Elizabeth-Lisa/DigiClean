package dao;

import models.Cleaner;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oCleanerDao implements CleanerDao{
    public final Sql2o sql2o;

    public Sql2oCleanerDao(Sql2o sql2o) {
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
    public List<Cleaner> getAllCleaners() {
        getDrivers();

        String sql = "SELECT * FROM cleaners";

        try(Connection conn = sql2o.open()){
            return  conn.createQuery(sql)
                    .executeAndFetch(Cleaner.class);
        }
    }

    @Override
    public void addCleaner(Cleaner cleaner) {
        getDrivers();

        String sql = "INSERT INTO cleaners (cleanerName, cleanerPassword, cleanerIdNo, cleanerPhone, status) VALUES (:cleanerName, :cleanerPassword, :cleanerIdNo, :cleanerPhone, :status)";
        try(Connection conn = sql2o.open()){
            int id = (int) conn.createQuery(sql,true)
                    .bind(cleaner)
                    .executeUpdate()
                    .getKey();
            cleaner.setId(id);
        }catch (Sql2oException e){
            e.printStackTrace();
        }

    }

    @Override
    public void updateCleaner(Cleaner cleaner, int id) {
        getDrivers();

        String sql = "UPDATE cleaners SET (cleanerName, cleanerPassword, cleanerIdNo, cleanerPhone, status, cleanerBio) = (:cleanerName, :cleanerPassword, :cleanerIdNo, :cleanerPhone, :status, :cleanerBio) WHERE id = :id";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .bind(cleaner)
                    .executeUpdate();
        }catch (Sql2oException e){
            e.printStackTrace();
        }

    }

    @Override
    public Cleaner findCleanerById(int id) {
        getDrivers();
        String sql = "SELECT * FROM cleaners where id = :id";

        try(Connection conn = sql2o.open()){
           return conn.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Cleaner.class);
        }
    }

    @Override
    public void deleteCleanerById(int id) {
        getDrivers();

        String sql = "DELETE FROM cleaners WHERE id = :id";

        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        }catch (Sql2oException e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteAllCleaners() {
        getDrivers();

        String sql = "DELETE FROM cleaners";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql)
                    .executeUpdate();
        }catch (Sql2oException e){
            e.printStackTrace();
        }

    }

    public Cleaner findCleanerByIdAndPassword(int cleanerIdNo, int cleanerPassword) {
        getDrivers();

        String sql = "SELECT * FROM cleaners WHERE cleanerIdNo = :cleanerIdNo AND cleanerPassword = :cleanerPassword";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(sql)
                    .addParameter("cleanerIdNo",cleanerIdNo)
                    .addParameter("cleanerPassword",cleanerPassword)
                    .executeAndFetchFirst(Cleaner.class);
        }
    }
}
