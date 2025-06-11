package edu.utn.application.usecase.deck;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.service.deck.IDeckService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

// Cambiar nombre a listDecksUseCase.
@Service
public class GetAllDecksUseCase {
    private final IDeckService deckService;

    public GetAllDecksUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public List<DeckDTO> execute() {
        List<IDeck> decks = deckService.getAllDecks();
        return decks.stream()
                .map(DeckMapper::toDTO) // Mapea los decks a DTOs
                .collect(Collectors.toList());
    }
}
