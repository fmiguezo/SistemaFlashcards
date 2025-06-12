package edu.utn.infrastructure.adapters.out.persistence.postgres;

import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.adapters.out.persistence.entities.FlashcardEntity;
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

    public RepositorioDeCardsPostgres(JpaFlashcardRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
        this.mapper = new FlashcardPersistenceMapper();
    }

    @Override
    public IFlashcard createCard(IFlashcard card) {
        FlashcardEntity entity = mapper.toPersistence(card);
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
        FlashcardEntity entity = mapper.toPersistence(card);
        jpaRepo.save(entity);
    }

    @Override
    public void deleteCard(UUID id) {
        jpaRepo.deleteById(id);
    }
}