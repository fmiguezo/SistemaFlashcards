package edu.utn.application.usecase;

import edu.utn.application.error.DeckError;
import edu.utn.domain.service.IDeckService;

import java.util.UUID;

public class DeleteDeckUseCase {
    private final IDeckService deckService;

    public DeleteDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public void execute(UUID deckId) {
        validateInput(deckId);
        deckService.deleteDeck(deckId);
    }

    private void validateInput(UUID deckId) {
        if (deckId == null) {
            throw DeckError.nullDeckId();
        }
        
        if (deckService.getDeckById(deckId) == null) {
            throw DeckError.deckNotFound();
        }
    }
}
