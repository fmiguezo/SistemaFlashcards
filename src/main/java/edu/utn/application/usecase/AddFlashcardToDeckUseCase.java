package edu.utn.application.usecase;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.DeckError;
import edu.utn.domain.model.Flashcard;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.IDeckService;
import edu.utn.domain.service.IFlashcardService;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AddFlashcardToDeckUseCase {
    private final IDeckService deckService;
    private final IFlashcardService flashcardService;
    private final CreateFlashcardUseCase createFlashcardUseCase;

    public AddFlashcardToDeckUseCase(IDeckService deckService, IFlashcardService flashcardService) {
        this.deckService = deckService;
        this.flashcardService = flashcardService;
        this.createFlashcardUseCase = new CreateFlashcardUseCase(flashcardService);
    }

    public void execute(UUID deckId, FlashcardDTO flashcardDTO) {
        validateInput(deckId, flashcardDTO);

        IDeck deck = deckService.getDeckById(deckId);
        if (deck == null) {
            throw DeckError.deckNotFound();
        }

        // Buscar si ya existe una flashcard en el deck con la misma pregunta y respuesta
        boolean exists = deck.getFlashcards().stream()
                .anyMatch(f -> f.getPregunta().equals(flashcardDTO.getPregunta()) && f.getRespuesta().equals(flashcardDTO.getRespuesta()));
        if (exists) {
            throw DeckError.flashcardAlreadyExists();
        }

        // Validar la flashcard usando la lógica de CreateFlashcardUseCase
        createFlashcardUseCase.validateInput(flashcardDTO);

        // Crear la flashcard y agregarla al deck
        IFlashcard newFlashcard = new Flashcard(flashcardDTO.getPregunta(), flashcardDTO.getRespuesta());
        flashcardService.addFlashcard(newFlashcard);
        deck.addFlashcard(newFlashcard);
        deckService.updateDeck(deck);
    }

    private void validateInput(UUID deckId, FlashcardDTO flashcardDTO) {
        if (deckId == null) {
            throw DeckError.nullDeckId();
        }
        if (flashcardDTO == null) {
            throw DeckError.nullFlashcard();
        }
    }
}
