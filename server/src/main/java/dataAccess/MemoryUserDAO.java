package dataAccess;

import dataAccess.DAOs.UserDAO;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

import javax.xml.crypto.Data;
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
    public void deleteUser(String username) throws DataAccessException {
        if (data.isEmpty()){
            throw new DataAccessException("message: Error: user not found");
        }
        else if (data.containsKey(username)){
            data.remove(username);
        }
        else {
            throw new DataAccessException("message: Error: user not found");
        }
    }

    @Override
    public void clear() {
        data.clear();
    }

    public int size(){
        return data.size();
    }
}
