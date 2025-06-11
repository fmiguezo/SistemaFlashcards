package edu.utn.application.usecase.flashcard;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.domain.service.deck.IDeckService;
import edu.utn.domain.service.flashcard.IFlashcardService;
import edu.utn.domain.service.validation.ValidationService;

import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AddFlashcardToDeckUseCase {
    private final IDeckService deckService;
    private final IFlashcardService flashcardService;
    private final ValidationService validationService;

    public AddFlashcardToDeckUseCase(IDeckService deckService, IFlashcardService flashcardService) {
        this.deckService = deckService;
        this.flashcardService = flashcardService;
        this.validationService = new ValidationService();
    }

    public FlashcardDTO execute(UUID deckId, FlashcardDTO flashcardDTO) {
        if (deckId == null) {
            throw DeckError.nullDeckId();
        }
        if (flashcardDTO == null) {
            throw DeckError.nullFlashcard();
        }

        IDeck deck = deckService.getDeckById(deckId);
        if (deck == null) {
            throw DeckError.deckNotFound();
        }

        boolean exists = deck.getFlashcards().stream()
                .anyMatch(f -> f.getPregunta().equals(flashcardDTO.getPregunta()) && f.getRespuesta().equals(flashcardDTO.getRespuesta()));
        if (exists) {
            throw DeckError.flashcardAlreadyExists();
        }

        validationService.validateFlashcardInput(flashcardDTO);

        IFlashcard newFlashcard = new Flashcard(flashcardDTO.getPregunta(), flashcardDTO.getRespuesta());
        flashcardService.addFlashcard(newFlashcard);
        deck.addFlashcard(newFlashcard);
        deckService.updateDeck(deck);
        return FlashcardMapper.toDTO(newFlashcard);
    }
}
