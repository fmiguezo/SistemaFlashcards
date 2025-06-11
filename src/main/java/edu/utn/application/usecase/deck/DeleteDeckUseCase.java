package edu.utn.application.usecase.deck;

import edu.utn.application.error.DeckError;
import edu.utn.domain.service.deck.IDeckService;

import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class DeleteDeckUseCase {
    private final IDeckService deckService;

    public DeleteDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public String execute(UUID id) {
        if (id == null) {
            throw DeckError.nullDeckId();
        }
        try {
            deckService.deleteDeckById(id);
            return "Deck borrado con éxito";
        } catch (Exception e) {
            return "Error al borrar deck: " + e.getMessage();
        }
    }
}
