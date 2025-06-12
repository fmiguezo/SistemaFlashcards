package edu.utn.infrastructure.adapters.out.persistence.postgres;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import edu.utn.infrastructure.ports.out.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RepositorioDeDecksPostgres implements IDeckRepository {

    private final JpaRepository jpaRepository;

    public RepositorioDeDecksPostgres(JpaRepository jpaRepository, DeckMapper mapper) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<IDeck> getAllDecks() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<IDeck> getDeckById(UUID id) {
        return jpaRepository.findDeckById(id);
    }

    @Override
    public void createDeck(IDeck deck) {
        jpaRepository.saveDeck(deck);
    }

    @Override
    public void updateDeck(IDeck deck) {
        jpaRepository.updateDeck(deck);
    }

    @Override
    public void deleteDeckById(UUID id) {
        jpaRepository.deleteDeckById(id);
    }

    @Override
    public List<IFlashcard> getFlashcardsByDeckId(UUID deckId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFlashcardsByDeckId'");
    }
}
