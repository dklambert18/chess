package dataAccess.DAOInterfaces;

import dataAccess.DataAccessException;

public interface AuthDAO {
    String createAuth(String username) throws DataAccessException;
    void deleteAuth(String auth);

//    AuthData getAuth(String authToken) throws DataAccessException;
    String getUser(String authToken) throws DataAccessException;
    int size() throws DataAccessException;
    void clear();

}
