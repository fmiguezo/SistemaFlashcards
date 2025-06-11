package edu.utn.application.usecase.deck;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.service.deck.IDeckService;

import java.util.UUID;

public class GetDeckUseCase {
    private final IDeckService deckService;

    public GetDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public DeckDTO execute(UUID deckId) {
        try {
            IDeck deck = this.deckService.getDeckById(deckId);
            return DeckMapper.toDTO(deck); // Mapeo del modelo a DTO
        } catch (DeckError e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
