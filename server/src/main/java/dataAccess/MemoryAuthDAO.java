package dataAccess;

import dataAccess.dataInterface.AuthDAO;
import model.AuthData;

import java.util.Map;

public class MemoryAuthData implements AuthDAO {
    private static Map<String, AuthData> data;
    MemoryAuthData(){
        data = null;
    }

    @Override
    public void createAuth(String username) {

    }

    @Override
    public void deleteAuth(AuthData data) throws DataAccessException {

    }

    @Override
    public void getAuth(String authToken) throws DataAccessException {

    }

    @Override
    public void clear() {
        data.clear();
    }
}
