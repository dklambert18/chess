package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataAccess.DataAccessException;
import dataAccess.ServiceErrors.ErrorResponse;
import dataAccess.ServiceErrors.ServiceErrorAlreadyTaken;
import dataAccess.ServiceErrors.ServiceErrorBadRequest;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import service.ClearService;
import service.handlers.*;
import service.responseObjects.*;
import spark.Request;
import spark.Response;
import spark.Spark;

import javax.xml.crypto.Data;

public class Server {
    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        Spark.delete("/db", this::clearData);
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);
//      Spark.exception(ResponseException.class, this::exceptionHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clearData(Request req, Response res) throws DataAccessException {
        ClearService service = new ClearService();
        service.clearApp();
        res.status(200);
        return new JsonObject();
    }

    private Object register(Request req, Response res) {
        RegisterHandler handler = new RegisterHandler(req);
        Object response = handler.register();

        //username contains the error info if something bad happened.
        if (response.getClass().equals(ServiceErrorAlreadyTaken.class)) {
            //check messages to see what exactly happened.
            res.status(403);
            response = new ErrorResponse("Error: already taken");
        }

        //checking for the other error case.
        else if (response.getClass().equals(ServiceErrorBadRequest.class)) {
            res.status(400);
            response = new ErrorResponse("Error: bad request");
        }

        //checking for any other error.
        else if (response.getClass().equals(Exception.class)){
            res.status(500);
            response = new ErrorResponse(((Exception) response).getMessage());
        }

        //everything worked and we return the RegisterResponse object
        else{
            res.status(200);
        }
        return new Gson().toJson(response);
    }

    private Object login(Request req, Response res) throws DataAccessException {
        LoginHandler loginHandler = new LoginHandler();
        Object response = loginHandler.login(req);

        //catching the error cases.
        if (response.getClass().equals(ServiceErrorUnauthorized.class)) {
            res.status(401);
            response = new ErrorResponse("Error: unauthorized");

        } else if (response.getClass().equals(DataAccessException.class)){
            res.status(500);
            response = new ErrorResponse(((DataAccessException) response).getMessage());
        }

        //everything was ok.
        return new Gson().toJson(response);
    }

    private Object logout(Request req, Response res) throws DataAccessException {
        LogoutHandler logoutHandler = new LogoutHandler();
        Object response = logoutHandler.logout(req);

        if (response.getClass().equals(ServiceErrorUnauthorized.class)) {
            res.status(401);
            response = new ErrorResponse("Error: unauthorized");
        }
        else if (response.getClass().equals(Exception.class)){
            res.status(500);
            response = new ErrorResponse(((Exception) response).getMessage());
        }
        else {
            res.status(200);
        }
        return new Gson().toJson(response);
    }

    private Object listGames(Request req, Response res) {
        ListGamesHandler handler = new ListGamesHandler();
        var response = handler.listGames(req);

        //check if the response object is an error object.
        if (response.getClass().equals(ServiceErrorUnauthorized.class)) {
            res.status(401);
            response = new ErrorResponse("Error: unauthorized");
            return new Gson().toJson(response);
        }
        else {
            res.status(200);
        }
        return new Gson().toJson(response);
    }

    private Object createGame(Request req, Response res) throws DataAccessException {
        CreateGameHandler handler = new CreateGameHandler();
        Object response = handler.createGame(req);

        if (response.getClass().equals(ServiceErrorBadRequest.class)) {
            res.status(400);
            response = new ErrorResponse("Error: bad request");
        }
        if (response.getClass().equals(ServiceErrorUnauthorized.class)) {
            res.status(401);
            response = new ErrorResponse("Error: unauthorized");
        }
        if (response.getClass().equals(DataAccessException.class)) {
            res.status(500);
            response = new ErrorResponse(((DataAccessException) response).getMessage());
        }
        return new Gson().toJson(response);
    }

    private Object joinGame(Request req, Response res) {
        var joinGameHandler = new JoinGameHandler();
        Object response = joinGameHandler.joinGame(req);

        if (response.getClass().equals(ServiceErrorBadRequest.class)){
            res.status(400);
            response = new ErrorResponse("Error: bad request");
        }
        else if (response.getClass().equals(ServiceErrorUnauthorized.class)){
            res.status(401);
            response = new ErrorResponse("Error: unauthorized");
        }
        else if (response.getClass().equals(ServiceErrorAlreadyTaken.class)){
            res.status(403);
            response = new ErrorResponse("Error: already taken");
        }
        else if (response.getClass().equals(DataAccessException.class)){
            res.status(500);
            return new Gson().toJson("hello there");
        } else {
            res.status(200);
            response = new JoinGameResponse();
        }
        return new Gson().toJson(response);
    }

}
