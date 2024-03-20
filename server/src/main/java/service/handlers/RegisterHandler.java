package service.handlers;

import com.google.gson.Gson;
import service.RegisterService;
import requestObjects.RegisterRequest;
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
