package dao;

import models.Cleaner;
import models.Client;

import java.util.List;

public interface CleanerDao {

    //List all cleaners
    List<Cleaner> getAllCleaners();

    //create a new cleaner
    void addCleaner(Cleaner cleaner);

    //get a specific cleaner
    Cleaner findCleanerById(int id);

    //Update a cleaner


    //Delete a cleaner
    void deleteCleanerById(int id);

    //Delete all cleaners
    void deleteAllCleaners();
}
