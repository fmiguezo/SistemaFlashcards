package application.usecase.flashcard;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.usecase.flashcard.AddFlashcardToDeckUseCase;
import edu.utn.domain.model.deck.Deck;
import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.domain.service.deck.IDeckService;
import edu.utn.domain.service.flashcard.IFlashcardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddFlashcardToDeckUseCaseTest {

    @Mock
    private IDeckService deckService;

    @Mock
    private IFlashcardService flashcardService;

    private AddFlashcardToDeckUseCase addFlashcardToDeckUseCase;
    private UUID validDeckId;
    private IDeck existingDeck;
    private FlashcardDTO newFlashcardDTO;
    private IFlashcard newFlashcard;

    @BeforeEach
    void setUp() {
        addFlashcardToDeckUseCase = new AddFlashcardToDeckUseCase(deckService, flashcardService);
        validDeckId = UUID.randomUUID();
        existingDeck = new Deck("Test Deck", "Test Description");
        newFlashcardDTO = new FlashcardDTO(
            null,
            "New Question",
            "New Answer",
            LocalDateTime.now(),
            null,
            LocalDateTime.now(),
            null,
            0
        );
        newFlashcard = new Flashcard("New Question", "New Answer");
    }

    @Test
    void execute_WithNewFlashcard_ShouldCreateAndAddToDeck() {
        when(deckService.getDeckById(validDeckId)).thenReturn(existingDeck);
        doNothing().when(flashcardService).addFlashcard(any(IFlashcard.class));
        doNothing().when(deckService).updateDeck(any(IDeck.class));

        addFlashcardToDeckUseCase.execute(validDeckId, newFlashcardDTO);

        assertTrue(existingDeck.getFlashcards().stream()
            .anyMatch(f -> f.getPregunta().equals("New Question") && f.getRespuesta().equals("New Answer")));
        verify(flashcardService).addFlashcard(any(IFlashcard.class));
        verify(deckService).updateDeck(existingDeck);
    }

    @Test
    void execute_WithExistingFlashcardInDeck_ShouldThrowException() {
        existingDeck.addFlashcard(newFlashcard);
        when(deckService.getDeckById(validDeckId)).thenReturn(existingDeck);

        DeckError exception = assertThrows(
            DeckError.class,
            () -> addFlashcardToDeckUseCase.execute(validDeckId, newFlashcardDTO)
        );
        assertEquals(DeckError.FLASHCARD_ALREADY_EXISTS, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithNonExistentDeck_ShouldThrowException() {
        when(deckService.getDeckById(validDeckId)).thenReturn(null);

        DeckError exception = assertThrows(
            DeckError.class,
            () -> addFlashcardToDeckUseCase.execute(validDeckId, newFlashcardDTO)
        );
        assertEquals(DeckError.DECK_NOT_FOUND, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithNullDeckId_ShouldThrowException() {
        DeckError exception = assertThrows(
            DeckError.class,
            () -> addFlashcardToDeckUseCase.execute(null, newFlashcardDTO)
        );
        assertEquals(DeckError.NULL_DECK_ID, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithNullFlashcardDTO_ShouldThrowException() {
        DeckError exception = assertThrows(
            DeckError.class,
            () -> addFlashcardToDeckUseCase.execute(validDeckId, null)
        );
        assertEquals(DeckError.NULL_FLASHCARD, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }
} 