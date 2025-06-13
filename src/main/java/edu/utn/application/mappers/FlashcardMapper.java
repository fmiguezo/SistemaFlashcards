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
        return flashcard;
    }
}
