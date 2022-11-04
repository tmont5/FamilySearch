package Services;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
import Request.PersonRequest;
import Result.PersonResult;
import com.sun.net.httpserver.HttpExchange;

public class PersonService {

    public PersonService() {}

    /**
     * Returns the single Person object with the specified ID
     * @param personRequest
     * @return
     */
    public PersonResult getPerson(PersonRequest personRequest) {
        PersonResult result = new PersonResult();
        Database db = new Database();
        //check that they are relatives
        try{
            db.openConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            if(authTokenDao.find(personRequest.getAuthToken()) != null){
                Person person = personDao.find(personRequest.getPersonID());
                AuthToken authToken = authTokenDao.find(personRequest.getAuthToken());
                if(person != null) {
                    if (person.getAssociatedUsername().equals(authToken.getUsername())) {
                        result.setAssociatedUsername(person.getAssociatedUsername());
                        result.setPersonID(person.getPersonID());
                        result.setFirstName(person.getFirstName());
                        result.setLastName(person.getLastName());
                        result.setGender(person.getGender());
                        result.setFatherID(person.getFatherID());
                        result.setMotherID(person.getMotherID());
                        result.setSpouseID(person.getSpouseID());
                        result.setSuccess(true);
                        result.setMessage("Successfully found person");
                    } else {
                        db.closeConnection(false);
                        result.setMessage("Error: Person does not belong to user");
                        result.setSuccess(false);
                        return result;
                    }
                }else{
                    db.closeConnection(false);
                    result.setMessage("Error: Person not found");
                    result.setSuccess(false);
                    return result;
                }
            } else{
                db.closeConnection(false);
                result.setMessage("Error: Invalid auth token");
                result.setSuccess(false);
                return result;
            }
        }catch (DataAccessException e){
            result.setSuccess(false);
            result.setMessage("Error: Internal server error");
            db.closeConnection(false);
            return result;
        }
        db.closeConnection(true);
        return result;

    }

    /**
     * This method will return all family members of the current user
     */
    public PersonResult getPersons(PersonRequest personRequest) {
        PersonResult result = new PersonResult();
        Database db = new Database();
        try{
            db.openConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            String associatedUsername;
            if(authTokenDao.find(personRequest.getAuthToken()) != null){
                associatedUsername = authTokenDao.find(personRequest.getAuthToken()).getUsername();
                result.setData(personDao.findAll(associatedUsername));
                result.setSuccess(true);
                result.setMessage("Successfully returned all family members of the current user");
            } else{
                result.setSuccess(false);
                result.setMessage("Error: Invalid auth token");
            }
            db.closeConnection(true);
            return result;
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            result.setMessage("Error: " + e.getMessage());
            result.setSuccess(false);
            return result;
        }
    }




}
