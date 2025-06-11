package edu.utn.application.usecase.deck;
import edu.utn.application.error.DeckError;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.service.deck.IDeckService;

import java.util.UUID;

public class GetDeckUseCase {
    private final IDeckService deckService;

    public GetDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public IDeck execute(UUID deckId) {
        try{
            return this.deckService.getDeckById(deckId);
        }catch (DeckError e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
