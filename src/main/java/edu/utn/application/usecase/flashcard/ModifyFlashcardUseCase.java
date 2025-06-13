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

    public FlashcardDTO execute(FlashcardDTO flashcardDTO, String pregunta, String respuesta) {
        FlashcardDTO existingFlashcard = validationService.validateFlashcardModification(flashcardDTO, flashcardService, pregunta, respuesta);
        existingFlashcard.setPregunta(pregunta);
        existingFlashcard.setRespuesta(respuesta);
        flashcardService.updateFlashcard(existingFlashcard);
        return existingFlashcard;
    }
}
