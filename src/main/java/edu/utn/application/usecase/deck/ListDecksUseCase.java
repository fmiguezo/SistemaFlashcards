package edu.utn.application.usecase.deck;

import edu.utn.application.dto.DeckDTO;
import edu.utn.domain.service.deck.IDeckService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListDecksUseCase {
    private final IDeckService deckService;

    public ListDecksUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public List<DeckDTO> execute() {
        return deckService.getAllDecks();
    }
}
