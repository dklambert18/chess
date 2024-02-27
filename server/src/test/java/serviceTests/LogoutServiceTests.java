package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.LogoutService;
import service.RegisterService;
import service.requestObjects.LogoutRequest;
import service.requestObjects.RegisterRequest;
import service.responseObjects.RegisterResponse;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutServiceTests {
    MemoryUserDAO userDAO = new MemoryUserDAO();
    MemoryAuthDAO authDAO = new MemoryAuthDAO();

    @BeforeEach
    void clear(){
        userDAO.clear();
        authDAO.clear();
    }

    @Test
    void logoutSuccess() throws Exception {
        var registerReq = new RegisterRequest("username", "password", "email");
        var registerService = new RegisterService();
        Object response = registerService.register(registerReq);
        var testAuth = ((RegisterResponse) response).authToken();

        //create a logout request from the auth token we got from registering the user.
        var logoutRequest = new LogoutRequest(testAuth);

        var logoutService = new LogoutService();
        var logoutResponse = logoutService.logout(logoutRequest);

        assertEquals(0, authDAO.size());
        assertEquals(1, userDAO.size());
        assertNull(logoutResponse.response());
        assertNull(authDAO.getUser(testAuth));
    }

    @Test
    void logoutFailure() throws Exception {
        var registerReq = new RegisterRequest("username", "password", "email");
        var registerService = new RegisterService();
        Object registerResponse = registerService.register(registerReq);

        //create a logout request from the auth token we got from registering the user.
        var request = new LogoutRequest("hello there obi-wan");

        var logoutService = new LogoutService();

        assertEquals(1, authDAO.size());
        assertEquals(1, userDAO.size());
        assertThrows(ServiceErrorUnauthorized.class, () -> {logoutService.logout(request);});
    }
}
