package net.ugurkartal.backend.services.messages;

public class PurchaseMessage {

    private PurchaseMessage() {}

    public static final String PRODUCT_NOT_FOUND = "Produkt nicht gefunden";
    public static final String SUPPLIER_NOT_FOUND = "Lieferant nicht gefunden";
    public static final String STOCK_NOT_ENOUGH = "Die Bestandsmenge darf nicht negativ sein";
}