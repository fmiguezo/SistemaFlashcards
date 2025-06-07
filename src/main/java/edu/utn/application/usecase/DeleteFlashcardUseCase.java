package edu.utn.application.usecase;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.IFlashcardService;

import java.util.UUID;

import org.springframework.stereotype.Service;
@Service
public class DeleteFlashcardUseCase {
    private final IFlashcardService flashcardService;

    public DeleteFlashcardUseCase(IFlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    public FlashcardDTO execute(UUID flashcardId) {
        if (flashcardId == null) {
            throw FlashcardError.nullFlashcardId();
        }

        IFlashcard flashcard = flashcardService.getFlashcardById(flashcardId);
        if (flashcard == null) {
            throw FlashcardError.flashcardNotFound();
        }

        flashcardService.deleteFlashcard(flashcardId);
        return FlashcardMapper.toDTO(flashcard);
    }
}
