package net.ugurkartal.backend.core.exceptions.types;

public class StockNotZeroException extends RuntimeException {
    public StockNotZeroException(String message) {
        super(message);
    }
}