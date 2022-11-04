package Services;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.AuthToken;
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
        UUID uuid = UUID.randomUUID();
        String authTokenString = uuid.toString();
        AuthToken authToken = new AuthToken(authTokenString, request.getUserName());

        try{
            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            if(userDao.find(request.getUserName()) != null){
                if(userDao.find(request.getUserName()).getPassword().equals(request.getPassword())){
                    authTokenDao.insert(authToken);
                    result.setAuthToken(authTokenString);
                    result.setUsername(request.getUserName());
                    result.setPersonID(userDao.find(request.getUserName()).getPersonID());
                    result.setSuccess(true);
                    result.setMessage("Successfully logged in");
                } else{
                    db.closeConnection(false);
                    result.setSuccess(false);
                    result.setMessage("Error: Incorrect password");
                    return result;
                }
            } else{
                db.closeConnection(false);
                result.setSuccess(false);
                result.setMessage("Error: User does not exist");
                return result;
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
