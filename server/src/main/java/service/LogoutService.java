package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MySQLAuthDAO;
import dataAccess.ServiceErrors.ServiceErrorUnauthorized;
import service.requestObjects.LogoutRequest;
import service.responseObjects.LogoutResponse;

public class LogoutService {
    MySQLAuthDAO authDAO = new MySQLAuthDAO();

    public LogoutService() throws DataAccessException {
    }

    public LogoutResponse logout(LogoutRequest r) throws ServiceErrorUnauthorized, DataAccessException {
        if (r.authToken() == null){
            throw new ServiceErrorUnauthorized();
        }
        if (authDAO.getUser(r.authToken()) == null){
            throw new ServiceErrorUnauthorized();
        }

        else {
            authDAO.deleteAuth(r.authToken());
            return new LogoutResponse(null);
        }
    }
}
