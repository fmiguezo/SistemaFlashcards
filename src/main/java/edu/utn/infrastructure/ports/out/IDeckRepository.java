package edu.utn.infrastructure.ports.out;
import edu.utn.domain.model.IDeck;
import java.util.List;
import java.util.UUID;

public interface IDeckRepository {
    List<IDeck> getAllDecks();
    IDeck getDeckById(UUID id);
    void createDeck(IDeck deck);
    void updateDeck(IDeck deck);
    void deleteDeckById(UUID id);
}
