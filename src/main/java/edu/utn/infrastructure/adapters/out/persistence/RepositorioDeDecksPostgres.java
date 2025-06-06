package edu.utn.infrastructure.adapters.out.persistence;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.domain.model.IDeck;
import edu.utn.infrastructure.adapters.out.exception.DeckNoExisteException;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import edu.utn.infrastructure.ports.out.JpaDeckRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public class RepositorioDeDecksPostgres implements IDeckRepository {

    private final JpaDeckRepository jpaDeckRepository;

    public RepositorioDeDecksPostgres(JpaDeckRepository jpaDeckRepository, DeckMapper mapper) {
        this.jpaDeckRepository = jpaDeckRepository;
    }

    @Override
    public List<IDeck> getAllDecks() {
        return jpaDeckRepository.findAll();
    }

    @Override
    public IDeck getDeckById(UUID id) {
        return jpaDeckRepository.findDeckById(id)
                .orElseThrow(() -> new DeckNoExisteException("Deck not found with id: " + id));
    }

    @Override
    public void createDeck(IDeck deck) {
        jpaDeckRepository.saveDeck(deck);
    }

    @Override
    public void updateDeck(IDeck deck) {
        jpaDeckRepository.updateDeck(deck);
    }

    @Override
    public void deleteDeckById(UUID id) {
        jpaDeckRepository.deleteDeckById(id);
    }
}
