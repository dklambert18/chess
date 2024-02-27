package dataAccess.ServiceErrors;

public class ServiceErrorBadRequest extends Exception {

    public ServiceErrorBadRequest() {
        super("Error: bad request");
    }

}
