package edu.utn.application.usecase;

import edu.utn.domain.model.Deck;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.service.IDeckService;
import edu.utn.application.error.DeckError;

public class CreateDeckUseCase {
    private final IDeckService deckService;

    public CreateDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public IDeck execute(String nombre, String descripcion) {
        validateInput(nombre, descripcion);
        
        Deck deck = new Deck(nombre, descripcion);
        deckService.addDeck(deck);
        
        return deck;
    }

    private void validateInput(String nombre, String descripcion) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw DeckError.emptyName();
        }
        
        if (nombre.length() > 100) {
            throw DeckError.nameTooLong();
        }
        
        if (descripcion != null && descripcion.length() > 250) {
            throw DeckError.descriptionTooLong();
        }
    }
}