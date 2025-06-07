package edu.utn.application.usecase;

import edu.utn.application.error.DeckError;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.service.IDeckService;

import java.util.UUID;

public class ModifyDeckUseCase {
    private final IDeckService deckService;
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 250;

    public ModifyDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public void execute(UUID deckId, String nuevoNombre, String nuevaDescripcion) {
        IDeck deck = validateInput(deckId, nuevoNombre, nuevaDescripcion);
        
        if (nuevoNombre != null) {
            deck.setNombre(nuevoNombre);
        }
        
        if (nuevaDescripcion != null) {
            deck.setDescripcion(nuevaDescripcion);
        }
        
        deckService.updateDeck(deck);
    }

    private IDeck validateInput(UUID deckId, String nuevoNombre, String nuevaDescripcion) {
        if (deckId == null) {
            throw DeckError.nullDeckId();
        }

        if (nuevoNombre == null && nuevaDescripcion == null) {
            throw DeckError.noFieldsToModify();
        }

        IDeck existingDeck = deckService.getDeckById(deckId);
        if (existingDeck == null) {
            throw DeckError.deckNotFound();
        }

        if (nuevoNombre != null) {
            if (nuevoNombre.trim().isEmpty()) {
                throw DeckError.emptyName();
            }
            if (nuevoNombre.length() > MAX_NAME_LENGTH) {
                throw DeckError.nameTooLong();
            }
            if (nuevoNombre.equals(existingDeck.getNombre())) {
                throw DeckError.sameName();
            }
        }

        if (nuevaDescripcion != null) {
            if (nuevaDescripcion.length() > MAX_DESCRIPTION_LENGTH) {
                throw DeckError.descriptionTooLong();
            }
            if (nuevaDescripcion.equals(existingDeck.getDescripcion())) {
                throw DeckError.sameDescription();
            }
        }

        return existingDeck;
    }
}
