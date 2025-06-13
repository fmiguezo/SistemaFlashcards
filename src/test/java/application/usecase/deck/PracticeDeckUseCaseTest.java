package application.usecase.deck;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.usecase.deck.PracticeDeckUseCase;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.service.deck.IDeckService;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PracticeDeckUseCaseTest {

    @Mock
    private IDeckService deckService;

    private PracticeDeckUseCase useCase;

    @Mock
    private IEstrategiaRepeticion estrategia;

    @Mock
    private IUserPracticeInputPort userInputPort;

    private DeckDTO deck;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new PracticeDeckUseCase(deckService);
        deck = new DeckDTO(
                "Test Deck",
                "Test Description"
        );
    }

    @Test
    void execute_WithValidArguments_ShouldCallService() {
        useCase.execute(deck, estrategia, userInputPort);

        verify(deckService, times(1)).practiceDeck(deck, estrategia, userInputPort);
    }

    @Test
    void execute_WithNullDeck_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(null, estrategia, userInputPort)
        );
        assertEquals("deck no puede ser null", exception.getMessage());
    }

    @Test
    void execute_WithNullEstrategia_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(deck, null, userInputPort)
        );
        assertEquals("estrategia no puede ser null", exception.getMessage());
    }

    @Test
    void execute_WithNullUserInputPort_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> useCase.execute(deck, estrategia, null)
        );
        assertEquals("userInputPort no puede ser null", exception.getMessage());
    }
}