package edu.utn.application.usecase.flashcard;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.flashcard.IFlashcard;
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
        IFlashcard flashcard = validationService.validateFlashcardModification(flashcardDTO, flashcardService);
        
        if (flashcardDTO.getPregunta() != null) {
            flashcard.setPregunta(flashcardDTO.getPregunta());
        }
        
        if (flashcardDTO.getRespuesta() != null) {
            flashcard.setRespuesta(flashcardDTO.getRespuesta());
        }
        
        flashcardService.updateFlashcard(flashcard);
        return FlashcardMapper.toDTO(flashcard);
    }
}
