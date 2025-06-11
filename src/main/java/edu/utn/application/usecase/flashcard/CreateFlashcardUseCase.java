package edu.utn.application.usecase.flashcard;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.service.flashcard.IFlashcardService;
import edu.utn.domain.service.validation.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class CreateFlashcardUseCase {
    private final IFlashcardService flashcardService;
    private final ValidationService validationService;

    public CreateFlashcardUseCase(IFlashcardService flashcardService) {
        this.flashcardService = flashcardService;
        this.validationService = new ValidationService();
    }

    public FlashcardDTO execute(FlashcardDTO flashcardDTO) {
        validationService.validateFlashcardInput(flashcardDTO);
        
        Flashcard flashcard = new Flashcard(flashcardDTO.getPregunta(), flashcardDTO.getRespuesta());
        flashcardService.addFlashcard(flashcard);
        
        return FlashcardMapper.toDTO(flashcard);
    }
}
