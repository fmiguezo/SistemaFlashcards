package edu.utn.application.usecase;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.service.IDeckService;
import edu.utn.domain.service.ValidationService;
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
        IDeck deck = validationService.validateDeckModification(deckDTO, deckService);
        
        if (deckDTO.getNombre() != null) {
            deck.setNombre(deckDTO.getNombre());
        }
        
        if (deckDTO.getDescripcion() != null) {
            deck.setDescripcion(deckDTO.getDescripcion());
        }
        
        deckService.updateDeck(deck);
        return DeckMapper.toDTO(deck);
    }
}
