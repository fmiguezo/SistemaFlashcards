package edu.utn.application.usecase.deck;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.error.DeckError;
import edu.utn.domain.service.deck.IDeckService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetDeckUseCase {
    private final IDeckService deckService;

    public GetDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public DeckDTO execute(UUID deckId) {
        try {
            return deckService.getDeckById(deckId);
        } catch (DeckError e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
