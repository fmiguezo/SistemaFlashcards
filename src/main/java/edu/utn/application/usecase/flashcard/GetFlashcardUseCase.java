package edu.utn.application.usecase.flashcard;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.domain.service.flashcard.IFlashcardService;

import java.util.UUID;

public class GetFlashcardUseCase {
    private final IFlashcardService flashcardService;

    public GetFlashcardUseCase(IFlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    public FlashcardDTO execute(UUID flashcardId) {
        try {
            IFlashcard flashcard = this.flashcardService.getFlashcardById(flashcardId);
            return FlashcardMapper.toDTO(flashcard); // Mapeo del modelo a DTO
        } catch (FlashcardError e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
