package rsp.exception;

public class IllegalActionException extends RspException {

    public IllegalActionException(String message) {
        super(message);
    }

    public static IllegalActionException create(String actionName, Object object) {
        return new IllegalActionException("Can't do " + actionName + " with " + object);
    }
}
