package edu.utn.application.mappers;

import edu.utn.application.dto.DeckDTO;
import edu.utn.domain.model.IDeck;

public class DeckMapper {
    public static DeckDTO toDTO(IDeck deck) {
        return new DeckDTO(
                deck.getId(),
                deck.getNombre(),
                deck.getDescripcion(),
                deck.getFlashcards()
        );
    }

}
