package dataAccess;

import dataAccess.DAOInterfaces.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;

import java.sql.SQLException;

public class MySQLAuthDAO implements AuthDAO {

    MySQLAuthDAO() throws DataAccessException {
        configureDatabase();
    }
    @Override
    public String createAuth(String username) {
        return null;
    }

    @Override
    public void deleteAuth(String auth) {

    }

    @Override
    public String getUser(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void clear() {

    }

    private void configureDatabase(){

    }
}
