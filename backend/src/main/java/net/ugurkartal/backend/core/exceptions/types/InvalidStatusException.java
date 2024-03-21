package net.ugurkartal.backend.core.exceptions.types;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String message) {
        super(message);
    }
}