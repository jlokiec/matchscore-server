package pl.matchscore.server.services.exceptions;

public class ReportAlreadyExistsException extends Exception {
    public ReportAlreadyExistsException() {
    }

    public ReportAlreadyExistsException(String message) {
        super(message);
    }

    public ReportAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public ReportAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
