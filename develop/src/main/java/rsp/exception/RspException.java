package rsp.exception;

public class RspException extends RuntimeException {

    public RspException() {
    }

    public RspException(String message) {
        super(message);
    }

    public RspException(String message, Throwable cause) {
        super(message, cause);
    }

    public RspException(Throwable cause) {
        super(cause);
    }
}
