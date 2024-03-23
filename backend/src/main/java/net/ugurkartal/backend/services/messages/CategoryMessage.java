package net.ugurkartal.backend.services.messages;

public class CategoryMessage {
    private CategoryMessage() {
    }

    public static final String CATEGORY_NOT_FOUND = "Kategorie nicht gefunden";
    public static final String CATEGORY_NAME_EXISTS = "Kategorie Name existiert bereits";
    public static final String CATEGORY_HAS_ACTIVE_PRODUCTS = "Kategorie hat aktives Produkt. Eine Kategorie mit einem aktiven Produkt kann nicht passiv gemacht werden.";
}