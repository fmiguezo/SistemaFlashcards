package edu.utn.application.mappers;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.model.flashcard.IFlashcard;

import java.lang.reflect.Field;

public class FlashcardMapper {
    public static FlashcardDTO toDTO(IFlashcard flashcard) {
        FlashcardDTO dto = new FlashcardDTO(
                flashcard.getPregunta(),
                flashcard.getRespuesta()
        );
        dto.setId(flashcard.getId());
        dto.setCreatedAt(flashcard.getCreatedAt());
        dto.setUpdatedAt(flashcard.getUpdatedAt());
        dto.setNextReviewDate(flashcard.getNextReviewDate());
        dto.setLastReviewDate(flashcard.getLastReviewDate());
        dto.setScore(flashcard.getScore());
        return dto;
    }

    public static IFlashcard toDomain(FlashcardDTO dto) {
        IFlashcard flashcard = new Flashcard(dto.getPregunta(), dto.getRespuesta());
        try {
            Field idField = Flashcard.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(flashcard, dto.getId());

            Field createdAtField = Flashcard.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(flashcard, dto.getCreatedAt());

            Field updatedAtField = Flashcard.class.getDeclaredField("updatedAt");
            updatedAtField.setAccessible(true);
            updatedAtField.set(flashcard, dto.getUpdatedAt());

            Field nextReviewField = Flashcard.class.getDeclaredField("nextReviewDate");
            nextReviewField.setAccessible(true);
            nextReviewField.set(flashcard, dto.getNextReviewDate());

            Field lastReviewField = Flashcard.class.getDeclaredField("lastReviewDate");
            lastReviewField.setAccessible(true);
            lastReviewField.set(flashcard, dto.getLastReviewDate());

            Field scoreField = Flashcard.class.getDeclaredField("score");
            scoreField.setAccessible(true);
            scoreField.set(flashcard, dto.getScore());
        } catch (Exception e) {
            e.getMessage();
        }

        return flashcard;
    }
}
