package edu.utn.application.usecase;

import edu.utn.application.error.FlashcardError;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.IFlashcardService;

import java.util.UUID;

public class ModifyFlashcardUseCase {
    private final IFlashcardService flashcardService;
    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 500;

    public ModifyFlashcardUseCase(IFlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    public void execute(UUID flashcardId, String nuevaPregunta, String nuevaRespuesta) {
        IFlashcard flashcard = validateInput(flashcardId, nuevaPregunta, nuevaRespuesta);
        
        if (nuevaPregunta != null) {
            flashcard.setPregunta(nuevaPregunta);
        }
        
        if (nuevaRespuesta != null) {
            flashcard.setRespuesta(nuevaRespuesta);
        }
        
        flashcardService.updateFlashcard(flashcard);
    }

    private IFlashcard validateInput(UUID flashcardId, String nuevaPregunta, String nuevaRespuesta) {
        if (flashcardId == null) {
            throw FlashcardError.nullFlashcardId();
        }

        if (nuevaPregunta == null && nuevaRespuesta == null) {
            throw new IllegalArgumentException("Debe proporcionar al menos una pregunta o respuesta para modificar");
        }

        IFlashcard existingFlashcard = flashcardService.getFlashcardById(flashcardId);
        if (existingFlashcard == null) {
            throw FlashcardError.flashcardNotFound();
        }

        if (nuevaPregunta != null) {
            if (nuevaPregunta.trim().isEmpty()) {
                throw FlashcardError.emptyQuestion();
            }
            if (nuevaPregunta.length() < MIN_LENGTH) {
                throw FlashcardError.questionTooShort();
            }
            if (nuevaPregunta.length() > MAX_LENGTH) {
                throw FlashcardError.questionTooLong();
            }
            if (nuevaPregunta.equals(existingFlashcard.getPregunta())) {
                throw FlashcardError.sameQuestion();
            }
        }

        if (nuevaRespuesta != null) {
            if (nuevaRespuesta.trim().isEmpty()) {
                throw FlashcardError.emptyAnswer();
            }
            if (nuevaRespuesta.length() < MIN_LENGTH) {
                throw FlashcardError.answerTooShort();
            }
            if (nuevaRespuesta.length() > MAX_LENGTH) {
                throw FlashcardError.answerTooLong();
            }
            if (nuevaRespuesta.equals(existingFlashcard.getRespuesta())) {
                throw FlashcardError.sameAnswer();
            }
        }

        return existingFlashcard;
    }
}
