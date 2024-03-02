package service;

import dataAccess.MemoryAccess.MemoryAuthDAO;
import dataAccess.MemoryAccess.MemoryGameDAO;
import dataAccess.MemoryAccess.MemoryUserDAO;

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
