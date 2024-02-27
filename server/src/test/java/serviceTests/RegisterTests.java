package serviceTests;

import com.google.gson.stream.JsonReader;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import dataAccess.ServiceErrors.ServiceErrorAlreadyTaken;
import dataAccess.ServiceErrors.ServiceErrorBadRequest;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.RegisterService;
import service.requestObjects.RegisterRequest;
import service.responseObjects.RegisterResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTests {
    MemoryUserDAO userDAO = new MemoryUserDAO();
    MemoryAuthDAO authDAO = new MemoryAuthDAO();

    public RegisterTests() throws ServiceErrorBadRequest, ServiceErrorAlreadyTaken {
    }

    @BeforeEach
    void clear(){
        userDAO.clear();
        authDAO.clear();
    }

    @Test
    void RegisterSuccess() throws Exception {
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterService service = new RegisterService();
        Object response = service.register(request);
        RegisterResponse res = (RegisterResponse) response;


        var newUser = new MemoryUserDAO();

        assertEquals("username", res.username());
        assertEquals(authDAO.getUser(res.authToken()), res.username());
        assertEquals(1, newUser.size());
        assertEquals(1, authDAO.size());

    }

    @Test
    void RegisterUsernameTaken() throws Exception {
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterService service = new RegisterService();

        service.register(request);

        assertThrows(ServiceErrorAlreadyTaken.class, () -> { service.register(request);});    }

    @Test
    void RegisterBadRequest() throws ServiceErrorBadRequest, ServiceErrorAlreadyTaken {
        RegisterRequest request1 = new RegisterRequest(null, "password", "email");
        RegisterService service1 = new RegisterService();

        assertThrows(ServiceErrorBadRequest.class, () -> service1.register(request1));

        // Test case 2: Null password
        RegisterRequest request2 = new RegisterRequest("username2", null, "email");
        RegisterService service2 = new RegisterService();

        assertThrows(ServiceErrorBadRequest.class, () -> service2.register(request2));

        // Test case 3: Null email
        RegisterRequest request3 = new RegisterRequest("username1", "password", null);
        RegisterService service3 = new RegisterService();


        assertThrows(ServiceErrorBadRequest.class, () -> service3.register(request3));}
}
