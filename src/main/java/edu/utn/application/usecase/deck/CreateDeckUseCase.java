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

    public DeckDTO execute(String nombre, String descripcion) {
        DeckDTO deckDTO = new DeckDTO(nombre, descripcion);
        validationService.validateDeckInput(deckDTO);
        deckService.addDeck(deckDTO);
        return deckDTO;
    }
}