package service.requestObjects;

public class JoinGameRequest {
    String authToken;
    String playerColor;
    int gameID;
    public JoinGameRequest(String auth, String color, int ID){
        playerColor = color;
        gameID = ID;
        authToken = auth;
    }

    public void setAuthToken(String auth){
        authToken = auth;
    }

    public String getAuthToken(){
        return authToken;
    }

    public int getGameID(){
        return gameID;
    }

    public String getPlayerColor(){
        return playerColor;
    }
}
