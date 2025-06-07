package edu.utn.application.error;

public class DeckError extends RuntimeException {
    // Errores de validación de datos
    public static final String EMPTY_NAME = "El nombre del deck no puede estar vacío";
    public static final String NAME_TOO_LONG = "El nombre del deck no puede tener más de 100 caracteres";
    public static final String DESCRIPTION_TOO_LONG = "La descripción del deck no puede tener más de 250 caracteres";
    public static final String SAME_NAME = "El nuevo nombre no puede ser igual al actual";
    public static final String SAME_DESCRIPTION = "La nueva descripción no puede ser igual a la actual";
    public static final String NO_FIELDS_TO_MODIFY = "No hay campos para modificar";

    // Errores de existencia
    public static final String DECK_NOT_FOUND = "No se encontró el deck a modificar";
    public static final String NULL_DECK_ID = "El ID del deck no puede ser nulo";

    private DeckError(String message) {
        super(message);
    }

    // Métodos estáticos para lanzar errores de validación
    public static DeckError emptyName() {
        return new DeckError(EMPTY_NAME);
    }

    public static DeckError nameTooLong() {
        return new DeckError(NAME_TOO_LONG);
    }

    public static DeckError descriptionTooLong() {
        return new DeckError(DESCRIPTION_TOO_LONG);
    }

    public static DeckError sameName() {
        return new DeckError(SAME_NAME);
    }

    public static DeckError sameDescription() {
        return new DeckError(SAME_DESCRIPTION);
    }

    public static DeckError noFieldsToModify() {
        return new DeckError(NO_FIELDS_TO_MODIFY);
    }

    // Métodos estáticos para lanzar errores de existencia
    public static DeckError deckNotFound() {
        return new DeckError(DECK_NOT_FOUND);
    }

    public static DeckError nullDeckId() {
        return new DeckError(NULL_DECK_ID);
    }
} 