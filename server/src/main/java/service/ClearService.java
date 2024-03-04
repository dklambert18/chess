package service;

import dataAccess.*;

public class ClearService {
    MySQLUserDAO user = new MySQLUserDAO();
    MySQLGameDAO game = new MySQLGameDAO();
    MySQLAuthDAO auth = new MySQLAuthDAO();

    public ClearService() throws DataAccessException {
    }

    public void clearApp() throws DataAccessException {
        user.clear();
        game.clear();
        auth.clear();
    }
}
