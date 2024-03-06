package DataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.MySQLAuthDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SQLAuthMethodTests {
    MySQLAuthDAO authDAO = new MySQLAuthDAO();

    public SQLAuthMethodTests() throws DataAccessException {
    }

    @BeforeEach
    void clear(){
        authDAO.clear();
    }

    @Test
    void createAuthSuccess() throws DataAccessException {
        String auth = authDAO.createAuth("username");
        assertEquals(1, authDAO.size());
    }

    @Test
    void createAuthFail() throws DataAccessException {
        assertThrows(RuntimeException.class, () -> {authDAO.createAuth(null);});
    }

    @Test
    void deleteAuthSuccess() throws DataAccessException {
        String auth = authDAO.createAuth("username");
        authDAO.deleteAuth(auth);
        assertEquals(0, authDAO.size());
    }

    @Test
    void deleteAuthFail() throws DataAccessException {
        String auth = authDAO.createAuth("username");
        authDAO.deleteAuth(auth + "bullshiz");
        assertEquals(1, authDAO.size());
    }

    @Test
    void getUserSuccess() throws DataAccessException {
        String auth = authDAO.createAuth("username");
        assertEquals("username", authDAO.getUser(auth));
    }

    @Test
    void getUserFail() throws DataAccessException {
        String auth = authDAO.createAuth("username");
        assertNotEquals("username", authDAO.getUser(auth + "bullshiz"));
    }

    @Test
    void sizeSuccess() throws DataAccessException {
        String auth = authDAO.createAuth("username");
        assertEquals(1, authDAO.size());
    }

    @Test
    void sizeFail() throws DataAccessException {
        String auth = authDAO.createAuth("username");
        assertNotEquals(1, authDAO.size() + 1);
    }
}
