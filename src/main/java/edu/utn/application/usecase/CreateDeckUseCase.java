package edu.utn.application.usecase;

import edu.utn.application.dto.DeckDTO;
import edu.utn.domain.model.Deck;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.service.IDeckService;

import static edu.utn.application.mappers.DeckMapper.toDTO;

public class CreateDeckUseCase {
    private final IDeckService deckService;

    public CreateDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public DeckDTO execute(String nombre, String descripcion) {
        IDeck deck = new Deck(nombre, descripcion);
        deckService.addDeck(deck);
        return toDTO(deck);
    }
}
