package serviceTests;

import dataAccess.*;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.LoginService;
import service.RegisterService;
import requestObjects.LoginRequest;
import requestObjects.RegisterRequest;
import responseObjects.LoginResponse;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTests {
    MySQLUserDAO userDAO = new MySQLUserDAO();
    MySQLAuthDAO authDAO = new MySQLAuthDAO();

    public LoginServiceTests() throws DataAccessException {
    }

    @BeforeEach
    void clear(){
        userDAO.clear();
        authDAO.clear();
    }

    @Test
    void Success() throws Exception {
        var registerService = new RegisterService();
        registerService.register(new RegisterRequest("username",
                "password", "email"));

        LoginRequest request = new LoginRequest("username", "password");
        LoginService service = new LoginService();
        LoginResponse response = (LoginResponse) service.login(request);

        //We check that the authToken was added to the database and that we were given the correct authToken when we
        //                                                                                                    logged in
        assertEquals("username", authDAO.getUser(response.authToken()));
        assertEquals(1, userDAO.size());
        assertEquals(2, authDAO.size());
    }

    @Test
    void loginFailure() throws Exception {
        LoginRequest request = new LoginRequest("username", "password");
        LoginService service = new LoginService();
        assertThrows(ServiceErrorUnauthorized.class, () -> {service.login(request);});
    }
}
