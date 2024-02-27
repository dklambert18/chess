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

    public void setGameID(int id){
        gameID = id;
    }

    public void setPlayerColor(String color){
        playerColor = color;
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
