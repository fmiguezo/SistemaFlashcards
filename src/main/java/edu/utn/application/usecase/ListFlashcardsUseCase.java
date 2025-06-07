package edu.utn.application.usecase;

import edu.utn.application.error.DeckError;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.IDeckService;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
@Service
public class ListFlashcardsUseCase {
    private final IDeckService deckService;

    public ListFlashcardsUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public List<IFlashcard> execute(UUID deckId) {
        if (deckId == null) {
            throw DeckError.nullDeckId();
        }

        IDeck deck = deckService.getDeckById(deckId);
        if (deck == null) {
            throw DeckError.deckNotFound();
        }

        return deckService.getFlashcardsByDeckId(deckId);
    }
}
