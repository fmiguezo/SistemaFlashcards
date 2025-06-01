package edu.utn.infrastructure.ports.out;
import edu.utn.domain.model.Deck;
import java.util.List;

public interface IDeckRepository {
    List<Deck> getDecks();
    Deck getDeckById(String id);
    void addDeck(Deck deck);
    void updateDeck(Deck deck);
    void updateDeckById(String id, Deck deck);
    void deleteDeck(Deck deck);
    void deleteDeckById(String id);
}
