package Result;

public class RegisterResult {

    private String authToken;
    private String userName;
    private String personID;
    private String message;
    boolean success;

    public RegisterResult(String authToken, String userName, String personID, String message, boolean success) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
        this.message = message;
        this.success = success;
    }

    public RegisterResult(String message){
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
