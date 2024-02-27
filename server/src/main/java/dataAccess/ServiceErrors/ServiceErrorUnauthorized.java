package dataAccess.ServiceErrors;

public class ServiceErrorUnauthorized extends Exception {
//    public static String message = "Error: unauthorized";
    public ServiceErrorUnauthorized() {
        super("Error: unauthorized");
    }

}
