package dataAccess;

import dataAccess.DAOInterfaces.UserDAO;
import dataAccess.DataAccessException;
import model.UserData;

public class MySQLUserDAO implements UserDAO {
    @Override
    public void createUser(String username, String password, String email) {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void clear() {

    }
}
