package Request;

import Result.FillResult;

public class FillRequest {

    private String username;
    private int generations;

    public FillRequest(String username, int generations){
        this.username = username;
        this.generations = generations;
    }

    public FillRequest(String userName){
        this.username = userName;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public String fill(String userName, int generations){
        return "fill";
    }

}
