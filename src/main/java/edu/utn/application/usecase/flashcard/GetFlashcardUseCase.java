package edu.utn.application.usecase.flashcard;
import edu.utn.application.error.FlashcardError;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.domain.service.flashcard.IFlashcardService;

import java.util.UUID;

public class GetFlashcardUseCase {
    private final IFlashcardService flashcardService;

    public GetFlashcardUseCase(IFlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    public IFlashcard execute(UUID deckId) {
        try{
            return this.flashcardService.getFlashcardById(deckId);
        }catch (FlashcardError e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
