package edu.utn.application.usecase;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.Flashcard;
import edu.utn.domain.service.IFlashcardService;
import edu.utn.application.error.FlashcardError;

public class CreateFlashcardUseCase {
    private final IFlashcardService flashcardService;

    public CreateFlashcardUseCase(IFlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    public FlashcardDTO execute(FlashcardDTO flashcardDTO) {
        validateInput(flashcardDTO);
        
        Flashcard flashcard = new Flashcard(flashcardDTO.getPregunta(), flashcardDTO.getRespuesta());
        flashcardService.addFlashcard(flashcard);
        
        return FlashcardMapper.toDTO(flashcard);
    }

    private void validateInput(FlashcardDTO flashcardDTO) {
        if (flashcardDTO == null) {
            throw FlashcardError.nullFlashcard();
        }

        String pregunta = flashcardDTO.getPregunta();
        if (pregunta == null || pregunta.trim().isEmpty()) {
            throw FlashcardError.emptyQuestion();
        }
        
        if (pregunta.length() > 100) {
            throw FlashcardError.questionTooLong();
        }
        
        String respuesta = flashcardDTO.getRespuesta();
        if (respuesta == null || respuesta.trim().isEmpty()) {
            throw FlashcardError.emptyAnswer();
        }
        
        if (respuesta.length() > 250) {
            throw FlashcardError.answerTooLong();
        }
    }
}
