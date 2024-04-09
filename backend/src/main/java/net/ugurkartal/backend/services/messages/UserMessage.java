package net.ugurkartal.backend.services.messages;

public class UserMessage {
    private UserMessage() {}
    public static final String UNAUTHORIZED_ACCESS_REQUEST = "Sie sind nicht berechtigt, auf diesen Benutzer zuzugreifen";
    public static final String USERNAME_ALREADY_EXISTS = "Benutzername existiert bereits";
    public static final String EMAIL_ALREADY_EXISTS = "E-Mail existiert bereits";
}