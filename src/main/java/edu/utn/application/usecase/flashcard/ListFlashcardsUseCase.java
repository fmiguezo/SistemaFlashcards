package edu.utn.application.usecase.flashcard;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.DeckError;
import edu.utn.domain.service.deck.IDeckService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ListFlashcardsUseCase {
    private final IDeckService deckService;

    public ListFlashcardsUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public List<FlashcardDTO> execute(UUID deckId) {
        if (deckId == null) {
            throw DeckError.nullDeckId();
        }

        DeckDTO deck = deckService.getDeckById(deckId);
        if (deck == null) {
            throw DeckError.deckNotFound();
        }
        
        List<FlashcardDTO> flashcards = deckService.getFlashcardsByDeckId(deckId);
        return flashcards;
    }
}
