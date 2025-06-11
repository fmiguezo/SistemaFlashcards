package edu.utn.application.usecase.deck;

import edu.utn.application.error.FlashcardError;
import edu.utn.domain.service.flashcard.IFlashcardService;

import java.util.UUID;

import org.springframework.stereotype.Service;
@Service
public class DeleteFlashcardUseCase {
    private final IFlashcardService flashcardService;

    public DeleteFlashcardUseCase(IFlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    public String execute(UUID flashcardId) {
        if (flashcardId == null) {
            throw FlashcardError.nullFlashcardId();
        }
        try {
            flashcardService.deleteFlashcard(flashcardId);
            return "Flashcard borrada con éxito";
        } catch (Exception e) {
            return "Error al borrar flashcard: " + e.getMessage();
        }
    }
}
