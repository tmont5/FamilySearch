package Services;

import DataAccess.*;
import Request.ClearRequest;
import Result.ClearResult;

public class ClearService {

    public ClearService() {}


    /**
     * Clear the database
     * @param clearRequest
     * @return
     */
    public ClearResult clear(ClearRequest clearRequest) throws DataAccessException {
        ClearResult result = new ClearResult();
        Database db = new Database();
        try{
            db.openConnection();
            UserDao userDao = new UserDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());
            PersonDao personDao = new PersonDao(db.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            userDao.clear();
            eventDao.clear();
            personDao.clear();
            authTokenDao.clear();
            db.closeConnection(true);
            result.setMessage("Clear succeeded");
            result.setSuccess(true);
            return result;
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            result.setMessage("Clear failed");
            result.setSuccess(false);
            return result;
        }
    }
}
