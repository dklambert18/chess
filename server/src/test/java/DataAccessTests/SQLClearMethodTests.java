package DataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.MySQLAuthDAO;
import dataAccess.MySQLGameDAO;
import dataAccess.MySQLUserDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SQLClearMethodTests {
    MySQLGameDAO gameDAO = new MySQLGameDAO();
    MySQLAuthDAO authDAO = new MySQLAuthDAO();
    MySQLUserDAO userDAO = new MySQLUserDAO();

    public SQLClearMethodTests() throws DataAccessException {
    }
    @Test
    void clearGames() throws DataAccessException {
        gameDAO.clear();
        assertEquals(0, gameDAO.size());
    }
    @Test
    void clearUsers() throws DataAccessException {
        userDAO.clear();
        assertEquals(0, userDAO.size());
    }

    @Test
    void clearAuth() throws DataAccessException {
        authDAO.clear();
        assertEquals(0, authDAO.size());
    }
}
