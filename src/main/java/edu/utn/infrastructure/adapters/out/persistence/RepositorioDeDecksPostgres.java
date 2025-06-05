package edu.utn.infrastructure.adapters.out.persistence;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.domain.model.Deck;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import edu.utn.infrastructure.ports.out.JpaDeckRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RepositorioDeDecksPostgres implements IDeckRepository {

    private final JpaDeckRepository jpaDeckRepository;
    private final DeckMapper mapper;

    public RepositorioDeDecksPostgres(JpaDeckRepository jpaDeckRepository, DeckMapper mapper) {
        this.jpaDeckRepository = jpaDeckRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Deck> getDecks() {
        return List.of();
    }

    @Override
    public Deck getDeckById(String id) {
        return null;
    }

    @Override
    public void addDeck(Deck deck) {

    }

    @Override
    public void updateDeck(Deck deck) {

    }

    @Override
    public void updateDeckById(String id, Deck deck) {

    }

    @Override
    public void deleteDeck(Deck deck) {

    }

    @Override
    public void deleteDeckById(String id) {

    }
}
