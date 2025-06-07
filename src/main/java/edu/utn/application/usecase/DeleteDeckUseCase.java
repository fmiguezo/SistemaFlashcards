package edu.utn.application.usecase;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.service.IDeckService;

import java.util.UUID;

import org.springframework.stereotype.Service;
@Service
public class DeleteDeckUseCase {
    private final IDeckService deckService;

    public DeleteDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public DeckDTO execute(UUID deckId) {
        if (deckId == null) {
            throw DeckError.nullDeckId();
        }
        IDeck deck = deckService.getDeckById(deckId);
        if (deck == null) {
            throw DeckError.deckNotFound();
        }

        deckService.deleteDeck(deckId);
        return DeckMapper.toDTO(deck);
    }
}
