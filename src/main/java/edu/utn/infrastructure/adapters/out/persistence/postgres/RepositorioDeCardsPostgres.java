package edu.utn.infrastructure.adapters.out.persistence.postgres;

import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.adapters.out.persistence.entities.DeckEntity;
import edu.utn.infrastructure.adapters.out.persistence.entities.FlashcardEntity;
import edu.utn.infrastructure.adapters.out.persistence.mapper.DeckPersistenceMapper;
import edu.utn.infrastructure.adapters.out.persistence.mapper.FlashcardPersistenceMapper;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import edu.utn.infrastructure.ports.out.JpaFlashcardRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RepositorioDeCardsPostgres implements IFlashcardRepository {
    private final JpaFlashcardRepository jpaRepo;
    private final FlashcardPersistenceMapper mapper;
    private final DeckPersistenceMapper deckPersistenceMapper;

    public RepositorioDeCardsPostgres(JpaFlashcardRepository jpaRepo, DeckPersistenceMapper deckPersistenceMapper) {
        this.jpaRepo = jpaRepo;
        this.mapper = new FlashcardPersistenceMapper();
        this.deckPersistenceMapper = deckPersistenceMapper;
    }

    @Override
    public IFlashcard createCard(IFlashcard card) {
        DeckEntity deckEntity = deckPersistenceMapper.toPersistence(card.getDeck());
        FlashcardEntity entity = FlashcardPersistenceMapper.toPersistence(card, deckEntity);
        FlashcardEntity saved = jpaRepo.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<IFlashcard> getCardById(UUID id) {
        return jpaRepo.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void updateCard(IFlashcard card) {
        DeckEntity deckEntity = deckPersistenceMapper.toPersistence(card.getDeck());
        FlashcardEntity entity = FlashcardPersistenceMapper.toPersistence(card, deckEntity);
        jpaRepo.save(entity);
    }

    @Override
    public void deleteCard(UUID id) {
        jpaRepo.deleteById(id);
    }
}