package Services;

import DataAccess.*;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;

import java.net.HttpURLConnection;
import java.sql.Connection;
import java.util.UUID;

public class RegisterService {


    public RegisterService(){}



    /**
     * This method will register a new user
     * @param registerRequest
     * @return
     */
    public RegisterResult register(RegisterRequest registerRequest){
        RegisterResult result = new RegisterResult();
        Database db = new Database();
        User user = new User();

        user.setUserName(registerRequest.getUserName());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setGender(registerRequest.getGender());
        user.setPersonID(user.getFirstName() + "_" + user.getLastName());

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            UserDao userDao = new UserDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());

            if(userDao.find(registerRequest.getUserName()) == null){
                userDao.insert(user);
                result.setSuccess(true);
                result.setAuthToken(UUID.randomUUID().toString());
                authTokenDao.insert(new Model.AuthToken(result.getAuthToken(), user.getUserName()));
                result.setPersonID(user.getFirstName() + "_" + user.getLastName());
                result.setUserName(user.getUserName());

                int generations = 4;
                FamilyTreeGenerator familyTreeGenerator = new FamilyTreeGenerator();
                familyTreeGenerator.generateFamilyTree(registerRequest.getUserName(),registerRequest.getGender(), generations, user, db.getConnection());

                //conn.commit();
                //personDao.updatePerson(user);

                db.closeConnection(true);

            } else{
                db.closeConnection(false);
                result.setSuccess(false);
                result.setMessage("Error: Username already exists");
                return result;
            }
        }catch (Exception e){
            db.closeConnection(false);
            e.printStackTrace();
            result.setAuthToken(null);
            result.setPersonID(null);
            result.setUserName(null);
            result.setSuccess(false);
            result.setMessage("Error: " + e.getMessage());
        }

        return result;
    }

}
