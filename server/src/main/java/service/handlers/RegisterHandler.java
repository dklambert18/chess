package service;

import com.google.gson.Gson;
import service.requestObjects.RegisterRequest;
import service.responseObjects.RegisterResponse;
import spark.Request;

public class RegisterHandler {
    Request r;
    public RegisterHandler(Request r){
        this.r = r;
    }

    public RegisterResponse register(){
        RegisterRequest req =  new Gson().fromJson(r.body(), RegisterRequest.class);
        if (req.email() == null || req.password() == null || req.username() == null){
            return new RegisterResponse("Message: Bad Request", null);
        }
        return new RegisterService().register(req);
    }
}
