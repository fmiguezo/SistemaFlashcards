package edu.utn.application.mappers;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.usecase.deck.GetDeckUseCase;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.domain.service.deck.DeckService;
import edu.utn.domain.service.deck.IDeckService;
import edu.utn.domain.service.flashcard.FlashcardService;
import edu.utn.domain.service.flashcard.IFlashcardService;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;

import java.util.UUID;


public class FlashcardMapper {
    public static FlashcardDTO toDTO(IFlashcard flashcard) {
        if (flashcard == null) return null;

        UUID deckId = flashcard.getDeck() != null ? flashcard.getDeck().getId() : null;

        FlashcardDTO dto = new FlashcardDTO(
                flashcard.getPregunta(),
                flashcard.getRespuesta(),
                deckId
        );

        dto.setId(flashcard.getId());
        dto.setCreatedAt(flashcard.getCreatedAt());
        dto.setUpdatedAt(flashcard.getUpdatedAt());
        dto.setNextReviewDate(flashcard.getNextReviewDate());
        dto.setLastReviewDate(flashcard.getLastReviewDate());
        dto.setScore(flashcard.getScore());

        return dto;
    }

    public static IFlashcard toDomain(FlashcardDTO dto, IDeck deck) {
        return new Flashcard(dto.getPregunta(), dto.getRespuesta(), deck);
    }
}