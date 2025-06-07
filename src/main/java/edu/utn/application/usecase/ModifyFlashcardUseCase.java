package edu.utn.application.usecase;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.IFlashcardService;
import edu.utn.domain.service.ValidationService;

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
