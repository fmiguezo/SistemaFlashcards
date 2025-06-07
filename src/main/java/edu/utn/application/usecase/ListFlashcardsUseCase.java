package edu.utn.application.usecase;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.IDeckService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
@Service
public class ListFlashcardsUseCase {
    private final IDeckService deckService;

    public ListFlashcardsUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public List<FlashcardDTO> execute(UUID deckId) {
        if (deckId == null) {
            throw DeckError.nullDeckId();
        }

        IDeck deck = deckService.getDeckById(deckId);
        if (deck == null) {
            throw DeckError.deckNotFound();
        }
        
        List<IFlashcard> flashcards = deckService.getFlashcardsByDeckId(deckId);
        List<FlashcardDTO> flashcardsDTO = new ArrayList<>();
        for (IFlashcard flashcard : flashcards) {
            flashcardsDTO.add(FlashcardMapper.toDTO(flashcard));
        }
        return flashcardsDTO;
    }
}
