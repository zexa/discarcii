package xyz.zexa.discarcii.exception;

public class UnknownCommandException extends RuntimeException {
    public UnknownCommandException(String description) {
        super(description);
    }
}
