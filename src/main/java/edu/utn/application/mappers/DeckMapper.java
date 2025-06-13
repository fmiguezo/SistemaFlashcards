package edu.utn.application.mappers;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.domain.model.deck.Deck;
import edu.utn.domain.model.deck.IDeck;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class DeckMapper {
    public static DeckDTO toDTO(IDeck deck) {
        List<FlashcardDTO> flashcardDTOs = deck.getFlashcards().stream()
                .map(flashcard -> FlashcardMapper.toDTO(flashcard))
                .collect(Collectors.toList());

        DeckDTO deckDTO = new DeckDTO(
                deck.getNombre(),
                deck.getDescripcion()
        );
        deckDTO.setId(deck.getId());
        deckDTO.setFlashcards(flashcardDTOs);

        return deckDTO;
    }


    public static IDeck toDomain(DeckDTO dto) {
        IDeck deck = new Deck(dto.getNombre(), dto.getDescripcion());

        if (dto.getFlashcards() != null) {
            for (FlashcardDTO flashcardDTO : dto.getFlashcards()) {
                deck.addFlashcard(FlashcardMapper.toDomain(flashcardDTO, deck));
            }
        }

        return deck;
    }
}