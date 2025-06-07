package edu.utn.application.usecase;

import edu.utn.domain.model.Flashcard;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.IFlashcardService;
import edu.utn.application.error.FlashcardError;

public class CreateFlashcardUseCase {
    private final IFlashcardService flashcardService;

    public CreateFlashcardUseCase(IFlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    public IFlashcard execute(String pregunta, String respuesta) {
        validateInput(pregunta, respuesta);
        
        Flashcard flashcard = new Flashcard(pregunta, respuesta);
        flashcardService.addFlashcard(flashcard);
        
        return flashcard;
    }

    private void validateInput(String pregunta, String respuesta) {
        if (pregunta == null || pregunta.trim().isEmpty()) {
            throw FlashcardError.emptyQuestion();
        }
        
        if (pregunta.length() > 100) {
            throw FlashcardError.questionTooLong();
        }
        
        if (respuesta == null || respuesta.trim().isEmpty()) {
            throw FlashcardError.emptyAnswer();
        }
        
        if (respuesta.length() > 250) {
            throw FlashcardError.answerTooLong();
        }
    }
}
