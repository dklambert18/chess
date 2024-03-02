package serviceTests;

import dataAccess.MemoryAccess.MemoryAuthDAO;
import dataAccess.MemoryAccess.MemoryUserDAO;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.LoginService;
import service.RegisterService;
import service.requestObjects.LoginRequest;
import service.requestObjects.RegisterRequest;
import service.responseObjects.LoginResponse;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTests {
    MemoryUserDAO userDAO = new MemoryUserDAO();
    MemoryAuthDAO authDAO = new MemoryAuthDAO();

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
