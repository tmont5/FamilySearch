package Services;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Model.Event;
import Request.EventRequest;
import Result.EventResult;

public class EventService {



    public EventService() {}



    /**
     * Returns the single Event object with the specified ID.
     * @param eventRequest
     * @return
     */
    public EventResult getEvent(EventRequest eventRequest) {
        EventResult eventResult = new EventResult();
        Database db = new Database();
        //check that they are relatives
        try{
            db.openConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());
            if(authTokenDao.find(eventRequest.getAuthToken()) != null) {
                Event event = eventDao.find(eventRequest.getEventID());
                if (event != null) {
                    AuthToken authToken = authTokenDao.find(eventRequest.getAuthToken());
                    if (event.getAssociatedUsername().equals(authToken.getUsername())) {
                        eventResult.setAssociatedUsername(event.getAssociatedUsername());
                        eventResult.setEventID(event.getEventID());
                        eventResult.setPersonID(event.getPersonID());
                        eventResult.setLatitude(event.getLatitude());
                        eventResult.setLongitude(event.getLongitude());
                        eventResult.setCountry(event.getCountry());
                        eventResult.setCity(event.getCity());
                        eventResult.setEventType(event.getEventType());
                        eventResult.setYear(event.getYear());
                        eventResult.setSuccess(true);
                        eventResult.setMessage("Successfully found event");
                    } else {
                        db.closeConnection(false);
                        eventResult.setMessage("Error: Event does not belong to user");
                        eventResult.setSuccess(false);
                        return eventResult;
                    }
                } else {
                    db.closeConnection(false);
                    eventResult.setMessage("Error: Event not found");
                    eventResult.setSuccess(false);
                    return eventResult;
                }
            }else {
                db.closeConnection(false);
                eventResult.setMessage("Error: Invalid auth token");
                eventResult.setSuccess(false);
                return eventResult;
            }
        }catch (DataAccessException e){
            eventResult.setSuccess(false);
            eventResult.setMessage("Error: Internal server error");
            db.closeConnection(false);
            return eventResult;
        }
        db.closeConnection(true);
        return eventResult;
    }

    /**
     * This method will return all events for all family members of the current user
     */
    public EventResult getEvents(EventRequest eventRequest) {
        EventResult result = new EventResult();
        Database db = new Database();
        try{
            db.openConnection();
            AuthTokenDao authTokenDao = new AuthTokenDao(db.getConnection());
            EventDao eventDao = new EventDao(db.getConnection());
            String associatedUsername;
            if(authTokenDao.find(eventRequest.getAuthToken()) != null){
                associatedUsername = authTokenDao.find(eventRequest.getAuthToken()).getUsername();
                result.setData(eventDao.findAll(associatedUsername));
                result.setSuccess(true);
                result.setMessage("Successfully found events");
            } else{
                db.closeConnection(false);
                result.setMessage("Error: Invalid auth token");
                result.setSuccess(false);
                return result;
            }
        }catch(DataAccessException e){
            result.setSuccess(false);
            result.setMessage("Error: Internal server error");
            db.closeConnection(false);
            return result;
        }
        db.closeConnection(true);
        return result;
    }

}
