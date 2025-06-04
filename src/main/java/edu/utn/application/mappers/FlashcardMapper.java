package edu.utn.application.mappers;

import edu.utn.application.dto.FlashcardDTO;

public class FlashcardMapper {
    public static FlashcardDTO toDTO(edu.utn.domain.model.IFlashcard flashcard) {
        FlashcardDTO dto = new FlashcardDTO(
                flashcard.getId(),
                flashcard.getPregunta(),
                flashcard.getRespuesta()
        );
        return dto;
    }

}
