package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAccess.MemoryAuthDAO;
import dataAccess.MemoryAccess.MemoryUserDAO;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import model.UserData;
import service.requestObjects.LoginRequest;
import service.responseObjects.LoginResponse;

public class LoginService {
    MemoryUserDAO userDAO = new MemoryUserDAO();
    MemoryAuthDAO authDAO = new MemoryAuthDAO();


    public Object login(LoginRequest req) throws ServiceErrorUnauthorized, DataAccessException {

        //Check for improper request body
        if (req.password() == null || req.username() == null){
            throw new DataAccessException("no username or password provided");
        }

        //Check if the supposed user even exists.
        UserData userData = userDAO.getUser(req.username());
        if (userData == null){
            throw new ServiceErrorUnauthorized();
        }

        //check if the user's password is correct.
        String password = userData.password();
        if (!password.equals(req.password())){
            throw new ServiceErrorUnauthorized();
        }

        //create a new auth for the user cuz he's a good user and logged in correctly.
        String auth = authDAO.createAuth(req.username());
        return new LoginResponse(userData.username(), auth);
    }
}
