package application.usecase.deck;
import edu.utn.application.usecase.deck.GetDeckUseCase;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.service.deck.IDeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.UUID;

import static edu.utn.application.error.DeckError.deckNotFound;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetDeckUseCaseTest {

    @Mock
    private IDeckService deckService;

    private GetDeckUseCase getDeckUseCase;

    @BeforeEach
    void setUp() {
        getDeckUseCase = new GetDeckUseCase(deckService);
    }

    @Test
    void execute_WithValidId_ShouldReturnDeck() {
        // Arrange
        UUID deckId = UUID.randomUUID();
        IDeck mockDeck = mock(IDeck.class);
        when(deckService.getDeckById(deckId)).thenReturn(mockDeck);

        // Act
        IDeck result = getDeckUseCase.execute(deckId);

        // Assert
        assertNotNull(result);
        assertEquals(mockDeck, result);
        verify(deckService, times(1)).getDeckById(deckId);
    }

    @Test
    void execute_WhenDeckServiceThrowsDeckError_ShouldReturnNullAndPrintMessage() {
        // Arrange
        UUID deckId = UUID.randomUUID();
        when(deckService.getDeckById(deckId)).thenThrow( deckNotFound());

        // Act
        IDeck result = getDeckUseCase.execute(deckId);

        // Assert
        assertNull(result);
        verify(deckService, times(1)).getDeckById(deckId);
    }
}
