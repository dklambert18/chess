package dataAccess.ServiceErrors;

public class ServiceErrorAlreadyTaken extends Exception {

    public ServiceErrorAlreadyTaken() {
        super("Error: already taken");
    }
}
