package service;

import dataAccess.*;
import dataAccess.ServiceErrors.ServiceErrorAlreadyTaken;
import dataAccess.ServiceErrors.ServiceErrorBadRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import requestObjects.RegisterRequest;
import responseObjects.RegisterResponse;

public class RegisterService {
    MySQLUserDAO userDAO = new MySQLUserDAO();
    MySQLAuthDAO auth = new MySQLAuthDAO();


    public RegisterService() throws DataAccessException {
    }

    public Object register(RegisterRequest req) throws ServiceErrorAlreadyTaken, ServiceErrorBadRequest, Exception{
        if (req.email() == null || req.password() == null || req.username() == null){
            throw new ServiceErrorBadRequest();
        }
        //try to access the user to see if the username already exists.
        var userData = userDAO.getUser(req.username());

        //if the username returns something that isn't null, the username is taken
        if (userData != null){
            throw new ServiceErrorAlreadyTaken();
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String newPassword = encoder.encode(req.password());
        //if the user didn't exist, then we create the successful response object.
        userDAO.createUser(req.username(), newPassword, req.email());

        //Then we give the user a new authToken.
        String authToken = auth.createAuth(req.username());

        //return the successful response object.
        return new RegisterResponse(req.username(), authToken);
    }
}
