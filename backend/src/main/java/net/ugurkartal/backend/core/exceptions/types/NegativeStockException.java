package net.ugurkartal.backend.core.exceptions.types;

public class NegativeStockException extends RuntimeException {
    public NegativeStockException(String message) {
        super(message);
    }
}