package application.usecase;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.usecase.AddFlashcardToDeckUseCase;
import edu.utn.domain.model.Deck;
import edu.utn.domain.model.Flashcard;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.IDeckService;
import edu.utn.domain.service.IFlashcardService;
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
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(existingDeck);
        doNothing().when(flashcardService).addFlashcard(any(IFlashcard.class));
        doNothing().when(deckService).updateDeck(any(IDeck.class));

        // Act
        addFlashcardToDeckUseCase.execute(validDeckId, newFlashcardDTO);

        // Assert
        assertTrue(existingDeck.getFlashcards().stream()
            .anyMatch(f -> f.getPregunta().equals("New Question") && f.getRespuesta().equals("New Answer")));
        verify(flashcardService).addFlashcard(any(IFlashcard.class));
        verify(deckService).updateDeck(existingDeck);
    }

    @Test
    void execute_WithExistingFlashcardInDeck_ShouldThrowException() {
        // Arrange
        existingDeck.addFlashcard(newFlashcard);
        when(deckService.getDeckById(validDeckId)).thenReturn(existingDeck);

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> addFlashcardToDeckUseCase.execute(validDeckId, newFlashcardDTO)
        );
        assertEquals(DeckError.FLASHCARD_ALREADY_EXISTS, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithNonExistentDeck_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(null);

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> addFlashcardToDeckUseCase.execute(validDeckId, newFlashcardDTO)
        );
        assertEquals(DeckError.DECK_NOT_FOUND, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithNullDeckId_ShouldThrowException() {
        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> addFlashcardToDeckUseCase.execute(null, newFlashcardDTO)
        );
        assertEquals(DeckError.NULL_DECK_ID, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithNullFlashcardDTO_ShouldThrowException() {
        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> addFlashcardToDeckUseCase.execute(validDeckId, null)
        );
        assertEquals(DeckError.NULL_FLASHCARD, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }
} 