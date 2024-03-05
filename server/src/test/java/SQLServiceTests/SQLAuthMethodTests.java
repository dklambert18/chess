package SQLServiceTests;

import dataAccess.DataAccessException;
import dataAccess.MySQLAuthDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals("username", )
    }
}
