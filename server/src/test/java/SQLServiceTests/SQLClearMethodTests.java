package SQLServiceTests;

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

    public void clearGames() throws DataAccessException {
        gameDAO.clear();
        assertEquals()
    }
}
