package application.usecase;

import edu.utn.application.error.DeckError;
import edu.utn.application.usecase.DeleteDeckUseCase;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.service.IDeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteDeckUseCaseTest {

    @Mock
    private IDeckService deckService;

    @Mock
    private IDeck deck;

    private DeleteDeckUseCase deleteDeckUseCase;
    private UUID validDeckId;

    @BeforeEach
    void setUp() {
        deleteDeckUseCase = new DeleteDeckUseCase(deckService);
        validDeckId = UUID.randomUUID();
    }

    //Verifica que cuando el deck existe, se elimina correctamente.
    @Test
    void execute_WithValidDeckId_ShouldDeleteDeck() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(deck);
        doNothing().when(deckService).deleteDeck(validDeckId);

        // Act & Assert
        assertDoesNotThrow(() -> deleteDeckUseCase.execute(validDeckId));
        verify(deckService, times(1)).deleteDeck(validDeckId);
    }

    @Test
    void execute_WithNullDeckId_ShouldThrowException() {
        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> deleteDeckUseCase.execute(null)
        );
        assertEquals(DeckError.NULL_DECK_ID, exception.getMessage());
        verify(deckService, never()).deleteDeck(any(UUID.class));
    }

    @Test
    void execute_WithNonExistentDeck_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(null);

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> deleteDeckUseCase.execute(validDeckId)
        );
        assertEquals(DeckError.DECK_NOT_FOUND, exception.getMessage());
        verify(deckService, never()).deleteDeck(any(UUID.class));
    }
} 