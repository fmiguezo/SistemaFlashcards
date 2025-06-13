package application.usecase.deck;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.usecase.deck.CreateDeckUseCase;
import edu.utn.domain.model.deck.Deck;
import edu.utn.domain.service.deck.IDeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

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
    }

    @Test
    void execute_WithValidDeckDTO_ShouldCreateDeck() {
        doNothing().when(deckService).addDeck(any(DeckDTO.class));

        DeckDTO result = createDeckUseCase.execute("Deck de prueba", "Descripción de prueba");

        assertNotNull(result);
        assertNotNull(result.getId());
        assertTrue(result.getFlashcards().isEmpty());
        verify(deckService, times(1)).addDeck(any(DeckDTO.class));
    }

    @Test
    void execute_WithNullName_ShouldThrowException() {
        DeckError exception = assertThrows(
            DeckError.class,
            () -> createDeckUseCase.execute(null, "Descripción de prueba")
        );
        assertEquals(DeckError.EMPTY_NAME, exception.getMessage());
        verify(deckService, never()).addDeck(any(DeckDTO.class));
    }

    @Test
    void execute_WithNameTooLong_ShouldThrowException() {
        DeckError exception = assertThrows(
            DeckError.class,
            () -> createDeckUseCase.execute("a".repeat(101), "Descripción de prueba"));
        assertEquals(DeckError.NAME_TOO_LONG, exception.getMessage());
        verify(deckService, never()).addDeck(any(DeckDTO.class));
    }

    @Test
    void execute_WithDescriptionTooLong_ShouldThrowException() {
        DeckError exception = assertThrows(
            DeckError.class,
            () -> createDeckUseCase.execute("Deck de prueba", "a".repeat(251))
        );
        assertEquals(DeckError.DESCRIPTION_TOO_LONG, exception.getMessage());
        verify(deckService, never()).addDeck(any(DeckDTO.class));
    }
} 
