package edu.utn.application.mappers;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.domain.model.deck.Deck;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;

import java.util.List;
import java.util.stream.Collectors;

public class DeckMapper {
    public static DeckDTO toDTO(IDeck deck) {
        List<FlashcardDTO> flashcardDTOs = deck.getFlashcards().stream()
                .map(FlashcardMapper::toDTO)
                .collect(Collectors.toList());

        return new DeckDTO(
                deck.getId(),
                deck.getNombre(),
                deck.getDescripcion(),
                flashcardDTOs
        );
    }


    public static IDeck toDomain(DeckDTO dto) {
        IDeck deck = new Deck(dto.getNombre(), dto.getDescripcion());

        try {
            java.lang.reflect.Field idField = Deck.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(deck, dto.getId());
        } catch (Exception e) {
            throw new RuntimeException("No se pudo asignar el ID al Deck por reflexión", e);
        }

        if (dto.getFlashcards() != null) {
            for (FlashcardDTO flashcardDTO : dto.getFlashcards()) {
                deck.addFlashcard(FlashcardMapper.toDomain(flashcardDTO));
            }
        }

        return deck;
    }
}