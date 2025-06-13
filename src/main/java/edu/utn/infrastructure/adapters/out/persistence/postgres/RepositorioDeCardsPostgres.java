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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@Transactional
public class RepositorioDeCardsPostgres implements IFlashcardRepository {
    private final JpaFlashcardRepository jpaRepo;
    private final JpaDeckRepository jpaDeckRepo;
    private final FlashcardPersistenceMapper mapper;
    private final DeckPersistenceMapper deckMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public RepositorioDeCardsPostgres(JpaFlashcardRepository jpaRepo,
                                      JpaDeckRepository jpaDeckRepo,
                                      FlashcardPersistenceMapper mapper,
                                      DeckPersistenceMapper deckMapper) {
        this.jpaRepo = jpaRepo;
        this.jpaDeckRepo = jpaDeckRepo;
        this.mapper = mapper;
        this.deckMapper = deckMapper;
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
        boolean exists = jpaRepo.existsById(id);
        System.out.println("Intentando eliminar flashcard con id: " + id);
        if (exists) {
            jpaRepo.deleteById(id);
            entityManager.flush();
            entityManager.clear();
            jpaRepo.flush();
        } else {
            throw new RuntimeException("Card no encontrado con id: " + id);
        }
    }

    @Override
    public List<IFlashcard> getFlashcardsByDeckId(UUID deckId) {
        DeckEntity deckEntity = jpaDeckRepo.findById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck no encontrado con id: " + deckId));

        IDeck deckDomain = deckMapper.toDomain(deckEntity);

        return jpaRepo.findByDeckId(deckId).stream()
                .map(entity -> mapper.toDomain(entity, deckDomain))
                .collect(Collectors.toList());
    }
}