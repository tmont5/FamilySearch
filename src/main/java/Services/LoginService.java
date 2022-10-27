package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Result.LoginResult;
import Request.LoginRequest;

import java.util.UUID;

public class LoginService {
    public LoginService() {
    }

    /**
     * Logs the user in and returns an auth token
     * @param request
     * @return
     */
    public LoginResult login(LoginRequest request) {
        LoginResult result = new LoginResult();
        Database db = new Database();
        //UUID uuid =  UUID.randomUUID();

        try{
            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            if(userDao.find(request.getUserName()) != null){
                if(userDao.find(request.getUserName()).getPassword().equals(request.getPassword())){
                    result.setAuthToken(UUID.randomUUID().toString());
                    result.setUsername(request.getUserName());
                    result.setPersonID(userDao.find(request.getUserName()).getPersonID());
                    result.setSuccess(true);
                    result.setMessage("Successfully logged in");
                } else{
                    result.setSuccess(false);
                    result.setMessage("Incorrect password");
                }
            } else{
                result.setSuccess(false);
                result.setMessage("User does not exist");
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
