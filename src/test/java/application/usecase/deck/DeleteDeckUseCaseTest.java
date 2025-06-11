package application.usecase.deck;

import edu.utn.application.error.DeckError;
import edu.utn.application.usecase.deck.DeleteDeckUseCase;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.service.deck.IDeckService;
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

    private DeleteDeckUseCase deleteDeckUseCase;
    private UUID validDeckId;

    @BeforeEach
    void setUp() {
        deleteDeckUseCase = new DeleteDeckUseCase(deckService);
        validDeckId = UUID.randomUUID();
    }

    @Test
    void execute_WithValidDeckId_ShouldDeleteDeckAndReturnSuccessMessage() {
        doNothing().when(deckService).deleteDeckById(validDeckId);

        String result = deleteDeckUseCase.execute(validDeckId);

        assertEquals("Deck borrado con éxito", result);
        verify(deckService, times(1)).deleteDeckById(validDeckId);
    }

    @Test
    void execute_WithNullDeckId_ShouldThrowException() {
        DeckError exception = assertThrows(
                DeckError.class,
                () -> deleteDeckUseCase.execute(null)
        );
        assertEquals(DeckError.NULL_DECK_ID, exception.getMessage());
        verify(deckService, never()).deleteDeckById(any(UUID.class));
    }

    @Test
    void execute_WhenDeleteThrowsException_ShouldReturnErrorMessage() {
        doThrow(new RuntimeException("Error inesperado")).when(deckService).deleteDeckById(validDeckId);

        String result = deleteDeckUseCase.execute(validDeckId);

        assertEquals("Error al borrar deck: Error inesperado", result);
        verify(deckService, times(1)).deleteDeckById(validDeckId);
    }
}