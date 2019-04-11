package pl.powiescdosukcesu.appuser;

public class AppUserNotFoundException extends RuntimeException {


    public AppUserNotFoundException() {
        super("User not found");
    }

    public AppUserNotFoundException(String message) {
        super(message);

    }

    public AppUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppUserNotFoundException(Throwable cause) {
        super(cause);
    }

    protected AppUserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
