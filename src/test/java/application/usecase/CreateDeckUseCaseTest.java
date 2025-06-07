package application.usecase;

import edu.utn.application.error.DeckError;
import edu.utn.application.usecase.CreateDeckUseCase;
import edu.utn.domain.model.Deck;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.service.IDeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateDeckUseCaseTest {

    @Mock
    private IDeckService deckService;

    private CreateDeckUseCase createDeckUseCase;
    private String validNombre;
    private String validDescripcion;

    @BeforeEach
    void setUp() {
        createDeckUseCase = new CreateDeckUseCase(deckService);
        validNombre = "Deck de prueba";
        validDescripcion = "Descripción de prueba";
    }

    @Test
    void execute_WithValidNameAndDescription_ShouldCreateDeck() {
        // Arrange
        doNothing().when(deckService).addDeck(any(Deck.class));

        // Act
        IDeck result = createDeckUseCase.execute(validNombre, validDescripcion);

        // Assert
        assertNotNull(result);
        assertEquals(validNombre, result.getNombre());
        assertEquals(validDescripcion, result.getDescripcion());
        verify(deckService, times(1)).addDeck(any(Deck.class));
    }

    @Test
    void execute_WithValidNameAndNullDescription_ShouldCreateDeck() {
        // Arrange
        doNothing().when(deckService).addDeck(any(Deck.class));

        // Act
        IDeck result = createDeckUseCase.execute(validNombre, null);

        // Assert
        assertNotNull(result);
        assertEquals(validNombre, result.getNombre());
        assertNull(result.getDescripcion());
        verify(deckService, times(1)).addDeck(any(Deck.class));
    }

    @Test
    void execute_WithNullName_ShouldThrowException() {
        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> createDeckUseCase.execute(null, validDescripcion)
        );
        assertEquals(DeckError.EMPTY_NAME, exception.getMessage());
        verify(deckService, never()).addDeck(any(Deck.class));
    }

    @Test
    void execute_WithEmptyName_ShouldThrowException() {
        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> createDeckUseCase.execute("", validDescripcion)
        );
        assertEquals(DeckError.EMPTY_NAME, exception.getMessage());
        verify(deckService, never()).addDeck(any(Deck.class));
    }

    @Test
    void execute_WithNameTooLong_ShouldThrowException() {
        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> createDeckUseCase.execute("a".repeat(101), validDescripcion)
        );
        assertEquals(DeckError.NAME_TOO_LONG, exception.getMessage());
        verify(deckService, never()).addDeck(any(Deck.class));
    }

    @Test
    void execute_WithDescriptionTooLong_ShouldThrowException() {
        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> createDeckUseCase.execute(validNombre, "a".repeat(251))
        );
        assertEquals(DeckError.DESCRIPTION_TOO_LONG, exception.getMessage());
        verify(deckService, never()).addDeck(any(Deck.class));
    }
} 
