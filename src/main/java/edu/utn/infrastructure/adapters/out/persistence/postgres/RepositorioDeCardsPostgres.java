package edu.utn.infrastructure.adapters.out.persistence.postgres;

import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.adapters.out.persistence.entities.DeckEntity;
import edu.utn.infrastructure.adapters.out.persistence.entities.FlashcardEntity;
import edu.utn.infrastructure.adapters.out.persistence.mapper.DeckPersistenceMapper;
import edu.utn.infrastructure.adapters.out.persistence.mapper.FlashcardPersistenceMapper;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import edu.utn.infrastructure.ports.out.JpaDeckRepository;
import edu.utn.infrastructure.ports.out.JpaFlashcardRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RepositorioDeCardsPostgres implements IFlashcardRepository {

    private final JpaFlashcardRepository jpaRepo;
    private final JpaDeckRepository jpaDeckRepo; // <-- Repositorio de Deck
    private final FlashcardPersistenceMapper mapper;
    private final DeckPersistenceMapper deckMapper;

    public RepositorioDeCardsPostgres(JpaFlashcardRepository jpaRepo, JpaDeckRepository jpaDeckRepo) {
        this.jpaRepo = jpaRepo;
        this.jpaDeckRepo = jpaDeckRepo;
        this.mapper = new FlashcardPersistenceMapper();
        this.deckMapper = new DeckPersistenceMapper(this.mapper);
    }

    @Override
    public IFlashcard createCard(IFlashcard card) {
        DeckEntity deckEntity = jpaDeckRepo.findById(card.getDeck().getId())
                .orElseThrow(() -> new RuntimeException("Deck no encontrado con id: " + card.getDeck().getId()));

        FlashcardEntity entity = FlashcardPersistenceMapper.toPersistence(card, deckEntity);
        FlashcardEntity saved = jpaRepo.save(entity);
        IDeck deckDomain = deckMapper.toDomain(deckEntity);
        return mapper.toDomain(saved, deckDomain);
    }

    @Override
    public Optional<IFlashcard> getCardById(UUID id) {
        return jpaRepo.findById(id)
                .map(entity -> {
                    DeckEntity deckEntity = entity.getDeck();
                    IDeck deckDomain = deckMapper.toDomain(deckEntity);
                    return mapper.toDomain(entity, deckDomain);
                });
    }


    @Override
    public void updateCard(IFlashcard card) {
        DeckEntity deckEntity = jpaDeckRepo.findById(card.getDeck().getId())
                .orElseThrow(() -> new RuntimeException("Deck no encontrado con id: " + card.getDeck().getId()));

        FlashcardEntity entity = FlashcardPersistenceMapper.toPersistence(card, deckEntity);
        jpaRepo.save(entity);
    }

    @Override
    public void deleteCard(UUID id) {
        jpaRepo.deleteById(id);
    }
}