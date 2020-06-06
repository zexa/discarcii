package xyz.zexa.discarcii.exception;

public class CommandException extends RuntimeException {
    public CommandException(String description) {
        super(description);
    }
}
