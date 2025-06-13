package edu.utn.application.usecase.flashcard;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.domain.service.flashcard.IFlashcardService;
import edu.utn.domain.service.validation.ValidationService;
import edu.utn.infrastructure.adapters.out.persistence.entities.DeckEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateFlashcardUseCase {
    private final IFlashcardService flashcardService;
    private final ValidationService validationService;

    public CreateFlashcardUseCase(IFlashcardService flashcardService) {
        this.flashcardService = flashcardService;
        this.validationService = new ValidationService();
    }

    public FlashcardDTO execute(String pregunta, String respuesta, DeckDTO deckDTO) {
        FlashcardDTO flashcardDTO = new FlashcardDTO(pregunta, respuesta, deckDTO);
        validationService.validateFlashcardInput(flashcardDTO);
        if (flashcardDTO.getId() == null) {
            flashcardDTO.setId(UUID.randomUUID());
        }
        if (flashcardDTO.getCreatedAt() == null) {
            flashcardDTO.setCreatedAt(LocalDateTime.now());
        }
        if (flashcardDTO.getUpdatedAt() == null) {
            flashcardDTO.setUpdatedAt(LocalDateTime.now());
        }
        if (flashcardDTO.getNextReviewDate() == null) {
            flashcardDTO.setNextReviewDate(LocalDateTime.now());
        }
        flashcardService.addFlashcard(flashcardDTO);
        return flashcardDTO;
    }
}
