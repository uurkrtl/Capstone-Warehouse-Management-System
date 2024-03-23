package net.ugurkartal.backend.core.exceptions.types;

public class HaveActiveProductException extends RuntimeException {
    public HaveActiveProductException(String message) {
        super(message);
    }
}