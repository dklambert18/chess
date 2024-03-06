package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.MySQLUserDAO;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SQLUserMethodTests {
    MySQLUserDAO userDAO = new MySQLUserDAO();

    @BeforeEach
    void clear(){
        userDAO.clear();
    }

    public SQLUserMethodTests() throws DataAccessException {
    }

    @Test
    void createUserSuccess() throws DataAccessException {
        userDAO.createUser("username", "password", "email");
        assertEquals(1, userDAO.size());
    }
    @Test
    void createUserFail() throws DataAccessException {
        userDAO.createUser("username", "password", "email");
        userDAO.createUser("username", "password", "email");
        assertEquals(1, userDAO.size());

    }

    @Test
    void getUserSuccess() throws DataAccessException {
        userDAO.createUser("username", "password", "email");
        UserData userData = userDAO.getUser("username");
        assertEquals(new UserData("username", "password", "email"), userData);
    }

    @Test
    void getUserFail() throws DataAccessException {
        userDAO.createUser("username", "password", "email");
        UserData userData = userDAO.getUser("wrong username");
        assertNotEquals(new UserData("username", "password", "email"), userData);
    }

    @Test
    void sizeSuccess() throws DataAccessException {
        userDAO.createUser("username", "password", "email");
        assertEquals(1, userDAO.size());
    }

    @Test
    void sizeFail() throws DataAccessException {
        userDAO.createUser("username", "password", "email");
        assertNotEquals(1, userDAO.size() + 1);
    }
}
