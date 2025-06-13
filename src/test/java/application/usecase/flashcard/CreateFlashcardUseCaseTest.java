package application.usecase.flashcard;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.usecase.flashcard.CreateFlashcardUseCase;
import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.service.flashcard.IFlashcardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateFlashcardUseCaseTest {

    @Mock
    private IFlashcardService flashcardService;

    private CreateFlashcardUseCase createFlashcardUseCase;
    private FlashcardDTO validFlashcardDTO;

    @BeforeEach
    void setUp() {
        createFlashcardUseCase = new CreateFlashcardUseCase(flashcardService);
    }

    @Test
    void execute_WithValidFlashcardDTO_ShouldCreateFlashcard() {
        doNothing().when(flashcardService).addFlashcard(any(FlashcardDTO.class));

        FlashcardDTO result = createFlashcardUseCase.execute("¿Cuál es la capital de Francia?",
                "La capital de Francia es París");

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertNotNull(result.getNextReviewDate());
        verify(flashcardService, times(1)).addFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithNullQuestion_ShouldThrowException() {
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute(null,
                    "La capital de Francia es París"));
        assertEquals(FlashcardError.EMPTY_QUESTION, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithEmptyQuestion_ShouldThrowException() {
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute( "",
                    "La capital de Francia es París"));
        assertEquals(FlashcardError.EMPTY_QUESTION, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithQuestionTooLong_ShouldThrowException() {
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute("a".repeat(101),
                    "Respuesta válida"));
        assertEquals(FlashcardError.QUESTION_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithNullAnswer_ShouldThrowException() {
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute("Pregunta válida",
                    null)
        );
        assertEquals(FlashcardError.EMPTY_ANSWER, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithEmptyAnswer_ShouldThrowException() {
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute("Pregunta válida",
                    "")
        );
        assertEquals(FlashcardError.EMPTY_ANSWER, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithAnswerTooLong_ShouldThrowException() {
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute("Pregunta válida",
                    "a".repeat(251))
        );
        assertEquals(FlashcardError.ANSWER_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(FlashcardDTO.class));
    }
} 