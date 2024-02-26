package dataAccess.dataInterface;

import dataAccess.DataAccessException;
import model.UserData;

public interface UserDAO {
    void createUser(String username, String password, String email);
    UserData getUser(String username) throws DataAccessException;
    void deleteUser(String username) throws DataAccessException;
    void clear();
}