package application.usecase.flashcard;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.usecase.flashcard.ListFlashcardsUseCase;
import edu.utn.domain.service.deck.IDeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import edu.utn.application.dto.FlashcardDTO;

@ExtendWith(MockitoExtension.class)
class ListFlashcardsUseCaseTest {

    @Mock
    private IDeckService deckService;

    private ListFlashcardsUseCase listFlashcardsUseCase;
    private DeckDTO testDeck;    // deck compartido para los DTOs
    private UUID validDeckId;

    @BeforeEach
    void setUp() {
        listFlashcardsUseCase = new ListFlashcardsUseCase(deckService);

        // Preparo un DeckDTO con ID
        validDeckId = UUID.randomUUID();
        testDeck = new DeckDTO("DeckTest", "DescTest");
        testDeck.setId(validDeckId);
    }

    @Test
    void execute_WithNullDeckId_ShouldThrowDeckError() {
        DeckError ex = assertThrows(DeckError.class, () -> listFlashcardsUseCase.execute(null));
        assertEquals(DeckError.NULL_DECK_ID, ex.getMessage());
        verifyNoInteractions(deckService);
    }

    @Test
    void execute_WithNonExistentDeck_ShouldThrowDeckError() {
        UUID deckId = UUID.randomUUID();
        when(deckService.getDeckById(deckId)).thenReturn(null);

        DeckError ex = assertThrows(DeckError.class, () -> listFlashcardsUseCase.execute(deckId));
        assertEquals(DeckError.DECK_NOT_FOUND, ex.getMessage());
        verify(deckService).getDeckById(deckId);
        verify(deckService, never()).getFlashcardsByDeckId(any());
    }

    @Test
    void execute_WithValidDeckId_ShouldReturnFlashcardsList() {
        // GIVEN
        UUID deckId = validDeckId;

        // Preparar los FlashcardDTO usando el constructor de 3 parámetros
        FlashcardDTO fc1 = new FlashcardDTO("Pregunta 1", "Respuesta 1", testDeck);
        FlashcardDTO fc2 = new FlashcardDTO("Pregunta 2", "Respuesta 2", testDeck);

        List<FlashcardDTO> flashcards = List.of(fc1, fc2);

        when(deckService.getDeckById(deckId)).thenReturn(testDeck);
        when(deckService.getFlashcardsByDeckId(deckId)).thenReturn(flashcards);

        // WHEN
        List<FlashcardDTO> result = listFlashcardsUseCase.execute(deckId);

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Pregunta 1", result.get(0).getPregunta());

        verify(deckService).getDeckById(deckId);
        verify(deckService).getFlashcardsByDeckId(deckId);
    }
}
