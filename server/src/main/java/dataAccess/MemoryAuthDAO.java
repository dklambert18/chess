package dataAccess;

import dataAccess.DAOs.AuthDAO;
import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {
    public static Map<String, String> data = new HashMap<>();
    public MemoryAuthDAO(){
    }

    @Override
    public String createAuth(String username) {
        String auth = UUID.randomUUID().toString();
        data.put(auth, username);
        return auth;
    }

    @Override
    public void deleteAuth(String auth) {
        if (!data.isEmpty()){
            data.remove(auth);
        }
    }

    @Override
    public String getUser(String authToken) {
        if (!data.isEmpty() && data.containsKey(authToken)){
            return data.get(authToken);
        }
        else {
            return null;
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
