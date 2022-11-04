package Result;



public class LoginResult {
    private String authtoken;
    private String username;
    private String personID;
    private String message;
    boolean success;

    public LoginResult() {}
    public LoginResult( String authToken, String username, String personID, String message, boolean success) {
        this.authtoken = authToken;
        this.username = username;
        this.personID = personID;
        this.message = message;
        this.success = success;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authToken) {
        this.authtoken = authToken;
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

