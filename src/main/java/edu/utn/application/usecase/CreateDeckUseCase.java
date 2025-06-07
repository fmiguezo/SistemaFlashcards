package edu.utn.application.usecase;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.domain.model.Deck;
import edu.utn.domain.service.IDeckService;
import edu.utn.application.error.DeckError;
import org.springframework.stereotype.Service;
@Service
public class CreateDeckUseCase {
    private final IDeckService deckService;

    public CreateDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public DeckDTO execute(DeckDTO deckDTO) {
        validateInput(deckDTO);
        
        Deck deck = new Deck(deckDTO.getNombre(), deckDTO.getDescripcion());
        deckService.addDeck(deck);
        
        return DeckMapper.toDTO(deck);
    }

    private void validateInput(DeckDTO deckDTO) {
        if (deckDTO == null) {
            throw DeckError.nullDeck();
        }

        String nombre = deckDTO.getNombre();
        if (nombre == null || nombre.trim().isEmpty()) {
            throw DeckError.emptyName();
        }
        
        if (nombre.length() > 100) {
            throw DeckError.nameTooLong();
        }
        
        String descripcion = deckDTO.getDescripcion();
        if (descripcion != null && descripcion.length() > 250) {
            throw DeckError.descriptionTooLong();
        }
    }
}