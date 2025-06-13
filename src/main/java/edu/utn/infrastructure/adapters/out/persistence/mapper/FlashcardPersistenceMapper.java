package edu.utn.infrastructure.adapters.out.persistence.mapper;

import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.adapters.out.persistence.entities.DeckEntity;
import edu.utn.infrastructure.adapters.out.persistence.entities.FlashcardEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class FlashcardPersistenceMapper {

    @Autowired
    private DeckPersistenceMapper deckPersistenceMapper;

    public FlashcardPersistenceMapper(@Lazy DeckPersistenceMapper deckPersistenceMapper) {
        this.deckPersistenceMapper = deckPersistenceMapper;
    }

    public IFlashcard toDomain(FlashcardEntity entity, IDeck deck) {
        if (entity == null) return null;

        IFlashcard flashcard = new Flashcard(entity.getPregunta(), entity.getRespuesta(), deck);
        flashcard.setId(entity.getId());
        flashcard.setUpdatedAt(entity.getUpdatedAt());
        flashcard.setNextReviewDate(entity.getNextReviewDate());
        flashcard.setLastReviewDate(entity.getLastReviewDate());
        flashcard.setScore(entity.getScore());

        return flashcard;
    }

    public IFlashcard toDomainSinDeck(FlashcardEntity entity, IDeck deckDomain) {
        if (entity == null) return null;

        Flashcard flashcard = new Flashcard(entity.getPregunta(), entity.getRespuesta(), deckDomain);
        flashcard.setId(entity.getId());
        flashcard.setUpdatedAt(entity.getUpdatedAt());
        flashcard.setNextReviewDate(entity.getNextReviewDate());
        flashcard.setLastReviewDate(entity.getLastReviewDate());
        flashcard.setScore(entity.getScore());

        return flashcard;
    }

    public static FlashcardEntity toPersistence(IFlashcard flashcard, DeckEntity deckEntity) {
        if (flashcard == null) return null;

        FlashcardEntity entity = new FlashcardEntity(flashcard.getPregunta(), flashcard.getRespuesta());

        entity.setId(flashcard.getId());
        entity.setUpdatedAt(flashcard.getUpdatedAt());
        entity.setNextReviewDate(flashcard.getNextReviewDate());
        entity.setLastReviewDate(flashcard.getLastReviewDate());
        entity.setScore(flashcard.getScore());

        entity.setDeck(deckEntity);

        return entity;
    }
}
