package net.ugurkartal.backend.services.messages;
public class ProductMessage {
    private ProductMessage() {
    }
    public static final String PRODUCT_NAME_ALREADY_EXISTS = "Produktname existiert bereits";
    public static final String PRODUCT_NOT_FOUND = "Produkt nicht gefunden";
    public static final String PRODUCT_STOCK_NOT_ZERO = "Es kann nicht passiv gemacht werden, da der Produktbestand nicht Null ist";
}