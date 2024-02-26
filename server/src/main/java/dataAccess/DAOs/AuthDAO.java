package dataAccess.dataInterface;

import dataAccess.DataAccessException;
import model.AuthData;

public interface AuthDAO {
    void createAuth(String username);
    void deleteAuth(AuthData data) throws DataAccessException;
    AuthData getAuth(String authToken) throws DataAccessException;
    void clear();

}
