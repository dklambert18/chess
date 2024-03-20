package requestObjects;

public class CreateGameRequest {
    String gameName;
    String authToken;

    public CreateGameRequest(String gameName, String authToken){
        this.gameName = gameName;
        this.authToken = authToken;
    }

    public CreateGameRequest(){
    }

    public CreateGameRequest(String authorization) {
    }


    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

    public String getGameName(){
        return gameName;
    }

    public String getAuthToken(){
        return authToken;
    }
}
