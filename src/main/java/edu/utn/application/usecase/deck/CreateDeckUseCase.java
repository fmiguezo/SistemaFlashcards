package edu.utn.application.usecase.deck;

import edu.utn.application.dto.DeckDTO;
import edu.utn.domain.service.deck.IDeckService;
import edu.utn.domain.service.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        if (deckDTO.getId() == null) {
            deckDTO.setId(UUID.randomUUID());
        }
        deckService.addDeck(deckDTO);
        return deckDTO;
    }
}