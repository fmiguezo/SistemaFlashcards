package edu.utn.application.usecase;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.domain.model.Flashcard;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.IFlashcardService;

import java.util.UUID;

public class CreateFlashcardUseCase {
    private final IFlashcardService flashcardService;

    public CreateFlashcardUseCase(IFlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    public FlashcardDTO ejecutar(UUID deckId, String pregunta, String respuesta) {
        IFlashcard flashcard = new Flashcard(pregunta, respuesta);
        flashcardService.addFlashcardToDeck(flashcard, deckId);
        return toDTO(flashcard);
    }

    private FlashcardDTO toDTO(IFlashcard flashcard) {
        return new FlashcardDTO(
                flashcard.getId(),
                flashcard.getPregunta(),
                flashcard.getRespuesta()
        );
    }
}
