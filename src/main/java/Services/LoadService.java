package Services;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;
import Result.LoginResult;
import passoffrequest.LoginRequest;

import java.sql.Connection;

public class LoadService {

    public LoadService() {}



    /**
     * Clears all the data from database
     *
     */
    public void clear() throws DataAccessException {
        Database db = new Database();
        try {
            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());
            userDao.clear();
            personDao.clear();
            eventDao.clear();
            db.closeConnection(true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }
    }

    /**
     * Loads data into the database
     * @param loadRequest
     * @return
     */
    public LoadResult load(LoadRequest loadRequest) {

        LoadResult result =  new LoadResult();
        Database db = new Database();
        try {

            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());

            if(loadRequest.getPerson()[0] == null || loadRequest.getUser()[0] == null || loadRequest.getEvent()[0] == null){
                throw new DataAccessException("Request property missing or has invalid value");
            }

            clear();

            for(User user : loadRequest.getUser()) {
                userDao.insert(user);
            }
            for(Person person : loadRequest.getPerson()) {
                personDao.insert(person);
            }
            for(Event event : loadRequest.getEvent()) {
                eventDao.insert(event);
            }
            db.closeConnection(true);
            result.setMessage("Successfully added " + loadRequest.getUser().length + " users, " + loadRequest.getPerson().length + " persons, and " + loadRequest.getEvent().length + " events to the database.");
            result.setSuccess(true);
            return result;
            }catch (DataAccessException e){
                db.closeConnection(false);
                e.printStackTrace();
                result.setMessage("Error: " + e.getMessage());
                result.setSuccess(false);
                return result;
            }
        }

}
