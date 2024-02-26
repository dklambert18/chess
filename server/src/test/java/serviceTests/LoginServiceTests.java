package serviceTests;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.LoginService;
import service.RegisterService;
import service.requestObjects.LoginRequest;
import service.requestObjects.RegisterRequest;
import service.responseObjects.LoginResponse;
import service.responseObjects.RegisterResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTests {
    MemoryUserDAO userDAO = new MemoryUserDAO();
    MemoryAuthDAO authDAO = new MemoryAuthDAO();

    @BeforeEach
    void clear(){
        userDAO.clear();
        authDAO.clear();
    }

    @Test
    void Success(){
        var registerService = new RegisterService();
        RegisterResponse registerResponse = registerService.register(new RegisterRequest("username",
                "password", "email"));

        LoginRequest request = new LoginRequest("username", "password");
        LoginService service = new LoginService();
        LoginResponse response = service.login(request);

        //We check that the authToken was added to the database and that we were given the correct authToken when we
        //                                                                                                    logged in
        assertEquals("username", authDAO.getUser(response.authToken()));
        assertEquals(1, userDAO.size());
        assertEquals(2, authDAO.size());
    }
}
