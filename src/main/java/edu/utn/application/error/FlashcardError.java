package edu.utn.application.error;

public class FlashcardError extends RuntimeException {
    // Errores de validación de datos
    public static final String EMPTY_QUESTION = "La pregunta no puede estar vacía";
    public static final String EMPTY_ANSWER = "La respuesta no puede estar vacía";
    public static final String QUESTION_TOO_SHORT = "La pregunta debe tener al menos 10 caracteres";
    public static final String ANSWER_TOO_SHORT = "La respuesta debe tener al menos 10 caracteres";
    public static final String QUESTION_TOO_LONG = "La pregunta no puede tener más de 500 caracteres";
    public static final String ANSWER_TOO_LONG = "La respuesta no puede tener más de 500 caracteres";
    public static final String SAME_QUESTION = "La nueva pregunta no puede ser igual a la actual";
    public static final String SAME_ANSWER = "La nueva respuesta no puede ser igual a la actual";

    // Errores de existencia
    public static final String FLASHCARD_NOT_FOUND = "No se encontró la flashcard a modificar";
    public static final String NULL_FLASHCARD_ID = "El ID de la flashcard no puede ser nulo";

    private FlashcardError(String message) {
        super(message);
    }

    // Métodos estáticos para lanzar errores de validación
    public static FlashcardError emptyQuestion() {
        return new FlashcardError(EMPTY_QUESTION);
    }

    public static FlashcardError emptyAnswer() {
        return new FlashcardError(EMPTY_ANSWER);
    }

    public static FlashcardError questionTooShort() {
        return new FlashcardError(QUESTION_TOO_SHORT);
    }

    public static FlashcardError answerTooShort() {
        return new FlashcardError(ANSWER_TOO_SHORT);
    }

    public static FlashcardError questionTooLong() {
        return new FlashcardError(QUESTION_TOO_LONG);
    }

    public static FlashcardError answerTooLong() {
        return new FlashcardError(ANSWER_TOO_LONG);
    }

    public static FlashcardError sameQuestion() {
        return new FlashcardError(SAME_QUESTION);
    }

    public static FlashcardError sameAnswer() {
        return new FlashcardError(SAME_ANSWER);
    }

    // Métodos estáticos para lanzar errores de existencia
    public static FlashcardError flashcardNotFound() {
        return new FlashcardError(FLASHCARD_NOT_FOUND);
    }

    public static FlashcardError nullFlashcardId() {
        return new FlashcardError(NULL_FLASHCARD_ID);
    }
} 