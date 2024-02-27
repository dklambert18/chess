package dataAccess.DAOs;

import dataAccess.DataAccessException;
import model.AuthData;

import javax.xml.crypto.Data;
import java.util.UUID;

public interface AuthDAO {
    String createAuth(String username);
    void deleteAuth(String auth);

//    AuthData getAuth(String authToken) throws DataAccessException;
    String getUser(String authToken) throws DataAccessException;
    void clear();

}
