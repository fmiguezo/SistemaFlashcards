package edu.utn.infrastructure.adapters.out.persistence.mapper;

import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.adapters.out.persistence.entities.FlashcardEntity;


public class FlashcardPersistenceMapper {

    public IFlashcard toDomain(FlashcardEntity entity) {
        if (entity == null) return null;

        IFlashcard flashcard = new Flashcard(entity.getPregunta(), entity.getRespuesta());

        flashcard.setUpdatedAt(entity.getUpdatedAt());
        flashcard.setNextReviewDate(entity.getNextReviewDate());
        flashcard.setLastReviewDate(entity.getLastReviewDate());
        flashcard.setScore(entity.getScore());

        return flashcard;
    }

    public static FlashcardEntity toPersistence(IFlashcard flashcard) {
        if (flashcard == null) return null;

        FlashcardEntity entity = new FlashcardEntity(flashcard.getPregunta(), flashcard.getRespuesta());

        entity.setId(flashcard.getId());
        entity.setUpdatedAt(flashcard.getUpdatedAt());
        entity.setNextReviewDate(flashcard.getNextReviewDate());
        entity.setLastReviewDate(flashcard.getLastReviewDate());
        entity.setScore(flashcard.getScore());

        return entity;
    }
}
