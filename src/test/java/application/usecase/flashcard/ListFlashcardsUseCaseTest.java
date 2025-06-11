package application.usecase.flashcard;

import edu.utn.application.error.DeckError;
import edu.utn.application.usecase.flashcard.ListFlashcardsUseCase;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.domain.service.deck.IDeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.utn.application.dto.FlashcardDTO;

@ExtendWith(MockitoExtension.class)
class ListFlashcardsUseCaseTest {

    @Mock
    private IDeckService deckService;

    @Mock
    private IDeck deck;

    @Mock
    private IFlashcard flashcard;

    private ListFlashcardsUseCase listFlashcardsUseCase;
    private UUID validDeckId;
    private List<IFlashcard> expectedFlashcards;

    @BeforeEach
    void setUp() {
        listFlashcardsUseCase = new ListFlashcardsUseCase(deckService);
        validDeckId = UUID.randomUUID();
        expectedFlashcards = new ArrayList<>();
        expectedFlashcards.add(flashcard);
    }

    @Test
    void execute_WithValidDeck_ShouldReturnFlashcards() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(deck);
        when(deckService.getFlashcardsByDeckId(validDeckId)).thenReturn(expectedFlashcards);

        // Act
        List<FlashcardDTO> result = listFlashcardsUseCase.execute(validDeckId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedFlashcards.size(), result.size());
        verify(deckService, times(1)).getFlashcardsByDeckId(validDeckId);
    }

    @Test
    void execute_WithNullDeckId_ShouldThrowException() {
        // Arrange
        UUID nullDeckId = null;

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> listFlashcardsUseCase.execute(nullDeckId)
        );
        assertEquals(DeckError.NULL_DECK_ID, exception.getMessage());
        verify(deckService, never()).getFlashcardsByDeckId(any(UUID.class));
    }

    @Test
    void execute_WithNonExistentDeck_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(null);

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> listFlashcardsUseCase.execute(validDeckId)
        );
        assertEquals(DeckError.DECK_NOT_FOUND, exception.getMessage());
        verify(deckService, never()).getFlashcardsByDeckId(any(UUID.class));
    }
} 