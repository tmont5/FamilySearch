package Services;

import DataAccess.*;
import Model.Person;
import Model.User;
import Request.FillRequest;
import Result.FillResult;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public class FillServices {

    public FillServices() {}


    /**
     * Fill in the database with a certain number of generations
     * @param fillRequest
     * @return
     */
    public FillResult fill(FillRequest fillRequest) throws DataAccessException {
        FillResult fillResult = new FillResult();
        Database db = new Database();

        int generations = 4;
        try {
            db.openConnection();
            Connection conn = db.getConnection();

            UserDao userDao = new UserDao(conn);
            User user = userDao.find(fillRequest.getUsername());

            clear(fillRequest, conn);

            FamilyTreeGenerator familyTreeGenerator = new FamilyTreeGenerator();
            familyTreeGenerator.generateFamilyTree(fillRequest.getUsername(), user.getGender(), generations, user, conn);

            db.closeConnection(true);
            fillResult.setMessage("Successfully added " + familyTreeGenerator.getPeopleCount() + " persons and " + familyTreeGenerator.getEventCount() + " events to the database.");
            fillResult.setSuccess(true);

        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return fillResult;
    }

    public FillResult fillGenerations(FillRequest fillRequest) throws DataAccessException {
        if(fillRequest.getGenerations() < 0) {
            throw new DataAccessException("Generations must be a positive integer");
        }

        FillResult fillResult = new FillResult();
        Database db = new Database();
        try{
            db.openConnection();
            Connection conn = db.getConnection();

            UserDao userDao = new UserDao(conn);
            User user = userDao.find(fillRequest.getUsername());
            if(user == null) {
                throw new DataAccessException("User not found");
            }

            clear(fillRequest, conn);

            FamilyTreeGenerator familyTreeGenerator = new FamilyTreeGenerator();
            familyTreeGenerator.generateFamilyTree(fillRequest.getUsername(), user.getGender(), fillRequest.getGenerations(), user, conn);

            db.closeConnection(true);
            fillResult.setMessage("Successfully added " + familyTreeGenerator.getPeopleCount() + " persons and " + familyTreeGenerator.getEventCount() + " events to the database.");
            fillResult.setSuccess(true);

            return fillResult;
        }catch (Exception e){
            db.closeConnection(false);
            fillResult.setMessage("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void clear(FillRequest fillRequest, Connection conn) throws DataAccessException, SQLException {
        try{
            UserDao userDao = new UserDao(conn);
            PersonDao personDao = new PersonDao(conn);
            EventDao eventDao = new EventDao(conn);
            AuthTokenDao authTokenDao = new AuthTokenDao(conn);

            //userDao.clearAssociatedUsers(fillRequest.getUsername());
            personDao.deleteAll(fillRequest.getUsername());
            eventDao.delete(fillRequest.getUsername());
            authTokenDao.clear();

            conn.commit();
        }catch (DataAccessException e) {
            conn.rollback();
            e.printStackTrace();
        }
    }
}
