package dataAccess;

import dataAccess.dataInterface.UserDAO;
import model.UserData;

import java.util.Map;

public class MemoryUserData implements UserDAO {
    private static Map<String, UserData> data;
    MemoryUserData(){
        data = null;
    }
    @Override
    public void createUser(String username, String password, String email) {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteUser(String username) throws DataAccessException {

    }

    @Override
    public void clear() {
        data.clear();
    }
}
