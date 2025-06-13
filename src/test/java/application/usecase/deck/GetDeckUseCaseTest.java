package application.usecase.deck;
import edu.utn.application.dto.DeckDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.usecase.deck.GetDeckUseCase;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.service.deck.IDeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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
    void execute_WithValidId_ShouldReturnDeckDTO() {
        UUID deckId = UUID.randomUUID();
        DeckDTO mockDeckDTO = new DeckDTO( "Deck Name", "Description");
        when(deckService.getDeckById(deckId)).thenReturn(mockDeckDTO);

        DeckDTO result = getDeckUseCase.execute(deckId);

        assertNotNull(result);
        assertEquals(mockDeckDTO, result);
        verify(deckService, times(1)).getDeckById(deckId);
    }

    @Test
    void execute_WhenDeckServiceThrowsDeckError_ShouldReturnNullAndPrintMessage() {
        UUID deckId = UUID.randomUUID();
        DeckError deckError = DeckError.deckNotFound();
        when(deckService.getDeckById(deckId)).thenThrow(deckError);

        DeckDTO result = getDeckUseCase.execute(deckId);
        assertNull(result);
        verify(deckService, times(1)).getDeckById(deckId);

    }
}

