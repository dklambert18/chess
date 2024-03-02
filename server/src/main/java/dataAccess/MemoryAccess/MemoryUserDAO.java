package dataAccess.MemoryAccess;

import dataAccess.DAOInterfaces.UserDAO;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO {
    private static Map<String, UserData> data = new HashMap<>();
    public MemoryUserDAO(){;
    }
    @Override
    public void createUser(String username, String password, String email) {
        UserData user = new UserData(username, password, email);
        data.put(username, user);
    }

    @Override
    public UserData getUser(String username) {
        if (data != null && data.containsKey(username)){
            return data.get(username);
        }
        return null;
    }

    @Override
    public void clear() {
        data.clear();
    }

    public int size(){
        return data.size();
    }
}
