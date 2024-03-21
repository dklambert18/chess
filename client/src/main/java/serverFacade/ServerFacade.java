package serverFacade;

import com.google.gson.Gson;
import requestObjects.*;
import responseObjects.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String Url) {
        this.serverUrl = Url;
    }

    public void clearData() throws Exception {
        var path = "/db";
        makeRequest("DELETE", path, null,null, null);
    }

    public RegisterResponse register(String username, String password, String email) throws Exception {
        var path = "/user";
        var request = new RegisterRequest(username, password, email);
        return makeRequest("POST", path, null, request, RegisterResponse.class);
    }

    public LoginResponse login(String username, String password) throws Exception {
        var path = "/session";
        var request = new LoginRequest(username, password);
        return makeRequest("POST", path, null, request, LoginResponse.class);
    }

    public LogoutResponse logout(String auth) throws Exception {
        var path = "/session";
        return makeRequest("DELETE", path, auth, null, LogoutResponse.class);
    }

    public ListGamesResponse listGames(String auth) throws Exception {
        var path = "/game";
        var request = new ListGamesRequest(auth);
        return makeRequest("GET", path, auth,null, ListGamesResponse.class);
    }

    public CreateGameResponse createGame(String gameName, String auth) throws Exception {
        var path = "/game";
        var request = new CreateGameRequest(gameName, auth);

        return makeRequest("POST", path, auth, request, CreateGameResponse.class);
    }

    public JoinGameResponse joinGame(String auth, int gameID, String color) throws Exception {
        var path = "/game";
        var request = new JoinGameRequest(auth, color, gameID);

        return makeRequest("PUT", path, auth, request, JoinGameResponse.class);
    }

    private <T> T makeRequest(String method, String path, String auth, Object request, Class<T> responseClass) throws Exception {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setRequestProperty("authorization", auth);
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws Exception {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new Exception("failure: " + status);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
