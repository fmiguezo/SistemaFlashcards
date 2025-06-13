package edu.utn.application.mappers;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.domain.model.deck.Deck;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.model.flashcard.IFlashcard;

import java.lang.reflect.Field;

public class FlashcardMapper {
    public static FlashcardDTO toDTO(IFlashcard flashcard, IDeck deck) {
        DeckDTO deckDTO = DeckMapper.toDTO(deck);
        FlashcardDTO dto = new FlashcardDTO(
                flashcard.getPregunta(),
                flashcard.getRespuesta(),
                deckDTO
        );
        dto.setId(flashcard.getId());
        dto.setCreatedAt(flashcard.getCreatedAt());
        dto.setUpdatedAt(flashcard.getUpdatedAt());
        dto.setNextReviewDate(flashcard.getNextReviewDate());
        dto.setLastReviewDate(flashcard.getLastReviewDate());
        dto.setScore(flashcard.getScore());
        return dto;
    }

    public static IFlashcard toDomain(FlashcardDTO dto, DeckDTO deck) {
        IDeck deckDomain = DeckMapper.toDomain(deck);
        IFlashcard flashcard = new Flashcard(dto.getPregunta(), dto.getRespuesta(), deckDomain);
        return flashcard;
    }
}
