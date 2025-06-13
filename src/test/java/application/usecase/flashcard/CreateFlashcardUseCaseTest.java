package application.usecase.flashcard;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.usecase.flashcard.CreateFlashcardUseCase;
import edu.utn.domain.service.flashcard.IFlashcardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateFlashcardUseCaseTest {

    @Mock
    private IFlashcardService flashcardService;

    private CreateFlashcardUseCase createFlashcardUseCase;
    private DeckDTO testDeck;  // deck de prueba

    @BeforeEach
    void setUp() {
        createFlashcardUseCase = new CreateFlashcardUseCase(flashcardService);

        // Creamos un DeckDTO de prueba
        testDeck = new DeckDTO("Deck Test", "Descripción Test");
        testDeck.setId(UUID.randomUUID());
    }

    @Test
    void execute_WithValidFlashcardDTO_ShouldCreateFlashcard() {
        // Preparar mock
        doNothing().when(flashcardService).addFlashcard(any(FlashcardDTO.class));

        // Ejecutar el caso de uso PASANDO el deck
        FlashcardDTO result = createFlashcardUseCase.execute(
                "¿Cuál es la capital de Francia?",
                "La capital de Francia es París",
                testDeck
        );

        // Verificaciones básicas
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getCreatedAt());
        assertEquals(testDeck, result.getDeck());

        // Capturar el DTO que recibió el service
        ArgumentCaptor<FlashcardDTO> captor = ArgumentCaptor.forClass(FlashcardDTO.class);
        verify(flashcardService, times(1)).addFlashcard(captor.capture());

        FlashcardDTO sent = captor.getValue();
        assertEquals("¿Cuál es la capital de Francia?", sent.getPregunta());
        assertEquals("La capital de Francia es París", sent.getRespuesta());
        assertEquals(testDeck, sent.getDeck());
    }

    @Test
    void execute_WithNullQuestion_ShouldThrowException() {
        FlashcardError ex = assertThrows(
                FlashcardError.class,
                () -> createFlashcardUseCase.execute(
                        null,
                        "La capital de Francia es París",
                        testDeck
                )
        );
        assertEquals(FlashcardError.EMPTY_QUESTION, ex.getMessage());
        verifyNoInteractions(flashcardService);
    }

    @Test
    void execute_WithEmptyQuestion_ShouldThrowException() {
        FlashcardError ex = assertThrows(
                FlashcardError.class,
                () -> createFlashcardUseCase.execute(
                        "",
                        "La capital de Francia es París",
                        testDeck
                )
        );
        assertEquals(FlashcardError.EMPTY_QUESTION, ex.getMessage());
        verifyNoInteractions(flashcardService);
    }

    @Test
    void execute_WithQuestionTooLong_ShouldThrowException() {
        String longQ = "a".repeat(101);
        FlashcardError ex = assertThrows(
                FlashcardError.class,
                () -> createFlashcardUseCase.execute(
                        longQ,
                        "Respuesta válida",
                        testDeck
                )
        );
        assertEquals(FlashcardError.QUESTION_TOO_LONG, ex.getMessage());
        verifyNoInteractions(flashcardService);
    }

    @Test
    void execute_WithNullAnswer_ShouldThrowException() {
        FlashcardError ex = assertThrows(
                FlashcardError.class,
                () -> createFlashcardUseCase.execute(
                        "Pregunta válida",
                        null,
                        testDeck
                )
        );
        assertEquals(FlashcardError.EMPTY_ANSWER, ex.getMessage());
        verifyNoInteractions(flashcardService);
    }

    @Test
    void execute_WithEmptyAnswer_ShouldThrowException() {
        FlashcardError ex = assertThrows(
                FlashcardError.class,
                () -> createFlashcardUseCase.execute(
                        "Pregunta válida",
                        "",
                        testDeck
                )
        );
        assertEquals(FlashcardError.EMPTY_ANSWER, ex.getMessage());
        verifyNoInteractions(flashcardService);
    }

    @Test
    void execute_WithAnswerTooLong_ShouldThrowException() {
        String longA = "a".repeat(251);
        FlashcardError ex = assertThrows(
                FlashcardError.class,
                () -> createFlashcardUseCase.execute(
                        "Pregunta válida",
                        longA,
                        testDeck
                )
        );
        assertEquals(FlashcardError.ANSWER_TOO_LONG, ex.getMessage());
        verifyNoInteractions(flashcardService);
    }
}
