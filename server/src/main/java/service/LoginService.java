package service;

import dataAccess.*;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.requestObjects.LoginRequest;
import service.responseObjects.LoginResponse;

public class LoginService {
    MySQLUserDAO userDAO = new MySQLUserDAO();
    MySQLAuthDAO authDAO = new MySQLAuthDAO();

    public LoginService() throws DataAccessException {
    }


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

        //check encryption.
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //check if the user's password is correct.
        String password = userData.password();
        if (!encoder.matches(req.password(), password)){
            throw new ServiceErrorUnauthorized();
        }

        //create a new auth for the user cuz he's a good user and logged in correctly.
        String auth = authDAO.createAuth(req.username());
        return new LoginResponse(userData.username(), auth);
    }
}
