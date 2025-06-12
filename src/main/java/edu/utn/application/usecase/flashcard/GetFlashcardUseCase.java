package edu.utn.application.usecase.flashcard;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.FlashcardError;
import edu.utn.domain.service.flashcard.IFlashcardService;

import java.util.UUID;

public class GetFlashcardUseCase {
    private final IFlashcardService flashcardService;

    public GetFlashcardUseCase(IFlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    public FlashcardDTO execute(UUID flashcardId) {
        try {
            return flashcardService.getFlashcardById(flashcardId);
        } catch (FlashcardError e) {
            e.getMessage();
            return null;
        }
    }
}
