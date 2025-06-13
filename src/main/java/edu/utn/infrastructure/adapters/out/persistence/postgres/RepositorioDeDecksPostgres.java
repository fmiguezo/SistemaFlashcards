package edu.utn.infrastructure.adapters.out.persistence.postgres;

import edu.utn.application.error.DeckError;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.adapters.out.persistence.entities.DeckEntity;
import edu.utn.infrastructure.adapters.out.persistence.mapper.DeckPersistenceMapper;
import edu.utn.infrastructure.adapters.out.persistence.mapper.FlashcardPersistenceMapper;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import edu.utn.infrastructure.ports.out.JpaDeckRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RepositorioDeDecksPostgres implements IDeckRepository {

    private final JpaDeckRepository jpaRepo;
    private final DeckPersistenceMapper mapper;
    private final FlashcardPersistenceMapper flashcardPersistenceMapper;
    private final DeckPersistenceMapper deckPersistenceMapper;

    @Autowired
    public RepositorioDeDecksPostgres(JpaDeckRepository jpaRepo,
                                      DeckPersistenceMapper mapper,
                                      FlashcardPersistenceMapper flashcardPersistenceMapper) {
        this.jpaRepo = jpaRepo;
        this.mapper = mapper;
        this.flashcardPersistenceMapper = flashcardPersistenceMapper;
        this.deckPersistenceMapper = new DeckPersistenceMapper(flashcardPersistenceMapper);
    }

    @Override
    @Transactional
    public List<IDeck> getAllDecks() {
        return jpaRepo.findAll()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
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
    @Transactional
    public List<IFlashcard> getFlashcardsByDeckId(UUID deckId) {
        return jpaRepo.findByIdWithFlashcards(deckId)
                .map(deckEntity -> {
                    IDeck deckDomain = deckPersistenceMapper.toDomain(deckEntity);
                    return deckEntity.getFlashcards().stream()
                            .map(flashcard -> flashcardPersistenceMapper.toDomain(flashcard, deckDomain))
                            .collect(Collectors.toList());
                })
                .orElseThrow(() -> DeckError.deckNotFound());
    }
}
