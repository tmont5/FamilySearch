package Services;

import Request.EventRequest;
import Result.EventResult;

public class EventService {

    public EventService() {}

    /**
     * Returns the single Event object with the specified ID.
     * @param eventID
     * @return
     */
    public EventResult getEvent(EventRequest eventRequest, String eventID) {
        //check if eventID is valid
        //if so, return event
        //else, return error message
        return null;
    }

    /**
     * This method will return all events for all family members of the current user
     */
    public EventResult getEvents(EventRequest eventRequest) {
        //Check if authToken is valid
        //If so, return all event data for the user
        return null;
    }

}
