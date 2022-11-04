package Model;

public class AuthToken {
    private String authtoken;
    private String username;

    public AuthToken(){}

    public AuthToken(String authToken, String userName) {
        this.authtoken = authToken;
        this.username = userName;

    }

    public String getAuthToken() {
        return authtoken;
    }

    public void setAuthToken(String authToken) {
        this.authtoken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

}

