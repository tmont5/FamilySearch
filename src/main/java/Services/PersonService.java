package Services;

import Request.PersonRequest;
import Result.PersonResult;

public class PersonService {

    public PersonService() {}

    /**
     * Returns the single Person object with the specified ID
     * @param personID
     * @return
     */
    public PersonResult getPerson(PersonRequest personRequest, String personID) {
        //check if personID is valid
        //if so, return person
        //else, return error message
        return null;
    }

    /**
     * This method will return all family members of the current user
     */
    public PersonResult getPersons(PersonRequest personRequest) {
        //Check if authToken is valid
        //If so, return all person data for the user
        return null;
    }




}
