package pl.matchscore.server.services.exceptions;

public class UsernamesNotMatchException extends Exception {
    public UsernamesNotMatchException() {
    }

    public UsernamesNotMatchException(String message) {
        super(message);
    }

    public UsernamesNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernamesNotMatchException(Throwable cause) {
        super(cause);
    }

    public UsernamesNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
