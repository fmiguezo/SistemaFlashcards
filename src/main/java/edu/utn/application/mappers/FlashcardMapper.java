package edu.utn.application.mappers;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.domain.model.IFlashcard;

public class FlashcardMapper {
    public static FlashcardDTO toDTO(IFlashcard flashcard) {
        FlashcardDTO dto = new FlashcardDTO(
                flashcard.getId(),
                flashcard.getPregunta(),
                flashcard.getRespuesta(),
                flashcard.getCreatedAt(),
                flashcard.getUpdatedAt(),
                flashcard.getNextReviewDate()
        );
        return dto;
    }
}
