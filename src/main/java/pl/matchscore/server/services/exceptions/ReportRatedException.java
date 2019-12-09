package pl.matchscore.server.services.exceptions;

public class ReportRatedException extends Exception {
    public ReportRatedException() {
    }

    public ReportRatedException(String message) {
        super(message);
    }

    public ReportRatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportRatedException(Throwable cause) {
        super(cause);
    }

    public ReportRatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
