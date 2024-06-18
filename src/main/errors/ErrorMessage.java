package main.errors;

public enum ErrorMessage {
    INVALID_COUNT_SOURCE_TRANSACTIONS("invalid count transactions. must be greater than 0");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
