package de.zaeaep.taskhub.common.error;

public abstract class ApiException extends RuntimeException {
    private final String errorCode;

    protected ApiException(String errorCode, String message) {

        super(message);
        this.errorCode = errorCode;
    }

    public String errorCode() {
        return errorCode;
    }
}
