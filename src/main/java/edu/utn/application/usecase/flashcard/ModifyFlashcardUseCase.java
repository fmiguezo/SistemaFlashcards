package edu.utn.application.usecase.flashcard;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.domain.service.flashcard.IFlashcardService;
import edu.utn.domain.service.validation.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class ModifyFlashcardUseCase {
    private final IFlashcardService flashcardService;
    private final ValidationService validationService;

    public ModifyFlashcardUseCase(IFlashcardService flashcardService) {
        this.flashcardService = flashcardService;
        this.validationService = new ValidationService();
    }

    public FlashcardDTO execute(FlashcardDTO flashcardDTO) {
        FlashcardDTO existingFlashcard = validationService.validateFlashcardModification(flashcardDTO, flashcardService);

        if (flashcardDTO.getPregunta() != null) {
            existingFlashcard.setPregunta(flashcardDTO.getPregunta());
        }

        if (flashcardDTO.getRespuesta() != null) {
            existingFlashcard.setRespuesta(flashcardDTO.getRespuesta());
        }

        flashcardService.updateFlashcard(existingFlashcard);
        return existingFlashcard;
    }
}
