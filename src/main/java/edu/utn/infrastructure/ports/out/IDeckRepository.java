package edu.utn.infrastructure.ports.out;
import edu.utn.application.dto.DeckDTO;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDeckRepository {
    List<IDeck> getAllDecks();
    Optional<IDeck> getDeckById(UUID id);
    void createDeck(IDeck deck);
    void updateDeck(IDeck deck);
    void deleteDeckById(UUID id);
    List<IFlashcard> getFlashcardsByDeckId(UUID id);
}
