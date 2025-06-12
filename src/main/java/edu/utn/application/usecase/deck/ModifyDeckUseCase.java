package edu.utn.application.usecase.deck;

import edu.utn.application.dto.DeckDTO;
import edu.utn.domain.service.deck.IDeckService;
import edu.utn.domain.service.validation.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class ModifyDeckUseCase {
    private final IDeckService deckService;
    private final ValidationService validationService;

    public ModifyDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
        this.validationService = new ValidationService();
    }

    public DeckDTO execute(DeckDTO deckDTO) {
        DeckDTO deck = validationService.validateDeckModification(deckDTO, deckService);
        
        if (deckDTO.getNombre() != null) {
            deck.setNombre(deckDTO.getNombre());
        }
        
        if (deckDTO.getDescripcion() != null) {
            deck.setDescripcion(deckDTO.getDescripcion());
        }
        
        deckService.updateDeck(deck);
        return deck;
    }
}
