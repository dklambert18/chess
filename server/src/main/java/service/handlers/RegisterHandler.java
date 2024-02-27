package service.handlers;

import com.google.gson.Gson;
import dataAccess.ServiceErrors.ServiceErrorAlreadyTaken;
import dataAccess.ServiceErrors.ServiceErrorBadRequest;
import service.RegisterService;
import service.requestObjects.RegisterRequest;
import service.responseObjects.RegisterResponse;
import spark.Request;

public class RegisterHandler {
    Request r;
    public RegisterHandler(Request r){
        this.r = r;
    }

    public Object register(){
        RegisterRequest req = new Gson().fromJson(r.body(), RegisterRequest.class);
        try {
            return new RegisterService().register(req);
        } catch (Exception e){
            return e;
        }
    }
}
