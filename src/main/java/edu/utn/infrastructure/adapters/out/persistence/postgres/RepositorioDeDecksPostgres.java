package edu.utn.infrastructure.adapters.out.persistence.postgres;

import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.adapters.out.persistence.entities.DeckEntity;
import edu.utn.infrastructure.adapters.out.persistence.mapper.DeckPersistenceMapper;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import edu.utn.infrastructure.ports.out.JpaDeckRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RepositorioDeDecksPostgres implements IDeckRepository {

    private final JpaDeckRepository jpaRepo;
    private final DeckPersistenceMapper mapper;


    public RepositorioDeDecksPostgres(JpaDeckRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
        mapper = new DeckPersistenceMapper();
    }

    @Override
    public List<IDeck> getAllDecks() {
        return jpaRepo.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<IDeck> getDeckById(UUID id) {
        return jpaRepo.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void createDeck(IDeck deck) {
        DeckEntity entity = mapper.toPersistence(deck);
        jpaRepo.save(entity);
    }

    @Override
    public void updateDeck(IDeck deck) {
        DeckEntity entity = mapper.toPersistence(deck);
        jpaRepo.save(entity);
    }

    @Override
    public void deleteDeckById(UUID id) {
        jpaRepo.deleteById(id);
    }

    @Override
    public List<IFlashcard> getFlashcardsByDeckId(UUID deckId) {
        // Implementar si tenés relación entre entidades
        throw new UnsupportedOperationException("Unimplemented method 'getFlashcardsByDeckId'");
    }
}
