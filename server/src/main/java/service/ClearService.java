package service;

import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;

public class ClearService {
    MemoryUserDAO user = new MemoryUserDAO();
    MemoryGameDAO game = new MemoryGameDAO();
    MemoryAuthDAO auth = new MemoryAuthDAO();
    public void clearApp() {
        user.clear();
        game.clear();
        auth.clear();
    }
}
