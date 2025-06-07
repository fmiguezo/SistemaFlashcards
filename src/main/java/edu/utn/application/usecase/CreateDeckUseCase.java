package edu.utn.application.usecase;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.domain.model.Deck;
import edu.utn.domain.service.IDeckService;
import edu.utn.domain.service.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class CreateDeckUseCase {
    private final IDeckService deckService;
    private final ValidationService validationService;

    public CreateDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
        this.validationService = new ValidationService();
    }

    public DeckDTO execute(DeckDTO deckDTO) {
        validationService.validateDeckInput(deckDTO);
        
        Deck deck = new Deck(deckDTO.getNombre(), deckDTO.getDescripcion());
        deckService.addDeck(deck);
        
        return DeckMapper.toDTO(deck);
    }
}