package net.ugurkartal.backend.services.messages;
public class ProductMessage {
    private ProductMessage() {
    }
    public static final String PRODUCT_NAME_ALREADY_EXISTS = "Produktname existiert bereits";
    public static final String PRODUCT_NOT_FOUND = "Produkt nicht gefunden";
    public static final String PRODUCT_STOCK_NOT_ZERO = "Produkte mit Lagerbestand können nicht passiv gemacht werden";
    public static final String CATEGORY_OF_PRODUCT_PASSIVE = "Das Produkt kann nicht aktiv gemacht werden, da die Kategorie passiv ist";
}