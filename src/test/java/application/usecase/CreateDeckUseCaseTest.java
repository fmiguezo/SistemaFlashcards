package application.usecase;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.usecase.CreateDeckUseCase;
import edu.utn.domain.model.Deck;
import edu.utn.domain.service.IDeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateDeckUseCaseTest {

    @Mock
    private IDeckService deckService;

    private CreateDeckUseCase createDeckUseCase;
    private DeckDTO validDeckDTO;

    @BeforeEach
    void setUp() {
        createDeckUseCase = new CreateDeckUseCase(deckService);
        validDeckDTO = new DeckDTO(null, "Deck de prueba", "Descripción de prueba", new ArrayList<>());
    }

    @Test
    void execute_WithValidDeckDTO_ShouldCreateDeck() {
        // Arrange
        doNothing().when(deckService).addDeck(any(Deck.class));

        // Act
        DeckDTO result = createDeckUseCase.execute(validDeckDTO);

        // Assert
        assertNotNull(result);
        assertEquals(validDeckDTO.getNombre(), result.getNombre());
        assertEquals(validDeckDTO.getDescripcion(), result.getDescripcion());
        assertNotNull(result.getId());
        assertTrue(result.getFlashcards().isEmpty());
        verify(deckService, times(1)).addDeck(any(Deck.class));
    }

    @Test
    void execute_WithValidDeckDTOAndNullDescription_ShouldCreateDeck() {
        // Arrange
        doNothing().when(deckService).addDeck(any(Deck.class));
        DeckDTO deckDTO = new DeckDTO(null, "Deck de prueba", null, new ArrayList<>());

        // Act
        DeckDTO result = createDeckUseCase.execute(deckDTO);

        // Assert
        assertNotNull(result);
        assertEquals(deckDTO.getNombre(), result.getNombre());
        assertNull(result.getDescripcion());
        assertNotNull(result.getId());
        assertTrue(result.getFlashcards().isEmpty());
        verify(deckService, times(1)).addDeck(any(Deck.class));
    }

    @Test
    void execute_WithNullDeckDTO_ShouldThrowException() {
        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> createDeckUseCase.execute(null)
        );
        assertEquals(DeckError.NULL_DECK, exception.getMessage());
        verify(deckService, never()).addDeck(any(Deck.class));
    }

    @Test
    void execute_WithNullName_ShouldThrowException() {
        // Arrange
        DeckDTO deckDTO = new DeckDTO(null, null, "Descripción de prueba", new ArrayList<>());

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> createDeckUseCase.execute(deckDTO)
        );
        assertEquals(DeckError.EMPTY_NAME, exception.getMessage());
        verify(deckService, never()).addDeck(any(Deck.class));
    }

    @Test
    void execute_WithEmptyName_ShouldThrowException() {
        // Arrange
        DeckDTO deckDTO = new DeckDTO(null, "", "Descripción de prueba", new ArrayList<>());

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> createDeckUseCase.execute(deckDTO)
        );
        assertEquals(DeckError.EMPTY_NAME, exception.getMessage());
        verify(deckService, never()).addDeck(any(Deck.class));
    }

    @Test
    void execute_WithNameTooLong_ShouldThrowException() {
        // Arrange
        DeckDTO deckDTO = new DeckDTO(null, "a".repeat(101), "Descripción de prueba", new ArrayList<>());

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> createDeckUseCase.execute(deckDTO)
        );
        assertEquals(DeckError.NAME_TOO_LONG, exception.getMessage());
        verify(deckService, never()).addDeck(any(Deck.class));
    }

    @Test
    void execute_WithDescriptionTooLong_ShouldThrowException() {
        // Arrange
        DeckDTO deckDTO = new DeckDTO(null, "Deck de prueba", "a".repeat(251), new ArrayList<>());

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> createDeckUseCase.execute(deckDTO)
        );
        assertEquals(DeckError.DESCRIPTION_TOO_LONG, exception.getMessage());
        verify(deckService, never()).addDeck(any(Deck.class));
    }
} 
