package edu.utn.application.usecase;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.application.error.DeckError;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.service.IDeckService;

public class ModifyDeckUseCase {
    private final IDeckService deckService;
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 250;

    public ModifyDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public DeckDTO execute(DeckDTO deckDTO) {
        IDeck deck = validateInput(deckDTO);
        
        if (deckDTO.getNombre() != null) {
            deck.setNombre(deckDTO.getNombre());
        }
        
        if (deckDTO.getDescripcion() != null) {
            deck.setDescripcion(deckDTO.getDescripcion());
        }
        
        deckService.updateDeck(deck);
        return DeckMapper.toDTO(deck);
    }

    private IDeck validateInput(DeckDTO deckDTO) {
        if (deckDTO == null) {
            throw DeckError.nullDeck();
        }

        if (deckDTO.getId() == null) {
            throw DeckError.nullDeckId();
        }

        if (deckDTO.getNombre() == null && deckDTO.getDescripcion() == null) {
            throw DeckError.noFieldsToModify();
        }

        IDeck existingDeck = deckService.getDeckById(deckDTO.getId());
        if (existingDeck == null) {
            throw DeckError.deckNotFound();
        }

        if (deckDTO.getNombre() != null) {
            if (deckDTO.getNombre().trim().isEmpty()) {
                throw DeckError.emptyName();
            }
            if (deckDTO.getNombre().length() > MAX_NAME_LENGTH) {
                throw DeckError.nameTooLong();
            }
            if (deckDTO.getNombre().equals(existingDeck.getNombre())) {
                throw DeckError.sameName();
            }
        }

        if (deckDTO.getDescripcion() != null) {
            if (deckDTO.getDescripcion().length() > MAX_DESCRIPTION_LENGTH) {
                throw DeckError.descriptionTooLong();
            }
            if (deckDTO.getDescripcion().equals(existingDeck.getDescripcion())) {
                throw DeckError.sameDescription();
            }
        }

        return existingDeck;
    }
}
