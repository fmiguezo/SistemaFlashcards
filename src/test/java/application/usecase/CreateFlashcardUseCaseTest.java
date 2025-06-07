package application.usecase;

import edu.utn.application.error.FlashcardError;
import edu.utn.application.usecase.CreateFlashcardUseCase;
import edu.utn.domain.model.Flashcard;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.IFlashcardService;
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
    private String validPregunta;
    private String validRespuesta;

    @BeforeEach
    void setUp() {
        createFlashcardUseCase = new CreateFlashcardUseCase(flashcardService);
        validPregunta = "¿Cuál es la capital de Francia?";
        validRespuesta = "La capital de Francia es París";
    }

    @Test
    void execute_WithValidQuestionAndAnswer_ShouldCreateFlashcard() {
        // Arrange
        doNothing().when(flashcardService).addFlashcard(any(Flashcard.class));

        // Act
        IFlashcard result = createFlashcardUseCase.execute(validPregunta, validRespuesta);

        // Assert
        assertNotNull(result);
        assertEquals(validPregunta, result.getPregunta());
        assertEquals(validRespuesta, result.getRespuesta());
        verify(flashcardService, times(1)).addFlashcard(any(Flashcard.class));
    }

    @Test
    void execute_WithNullQuestion_ShouldThrowException() {
        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute(null, validRespuesta)
        );
        assertEquals(FlashcardError.EMPTY_QUESTION, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(Flashcard.class));
    }

    @Test
    void execute_WithEmptyQuestion_ShouldThrowException() {
        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute("", validRespuesta)
        );
        assertEquals(FlashcardError.EMPTY_QUESTION, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(Flashcard.class));
    }

    @Test
    void execute_WithQuestionTooLong_ShouldThrowException() {
        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute("a".repeat(101), validRespuesta)
        );
        assertEquals(FlashcardError.QUESTION_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(Flashcard.class));
    }

    @Test
    void execute_WithNullAnswer_ShouldThrowException() {
        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute(validPregunta, null)
        );
        assertEquals(FlashcardError.EMPTY_ANSWER, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(Flashcard.class));
    }

    @Test
    void execute_WithEmptyAnswer_ShouldThrowException() {
        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute(validPregunta, "")
        );
        assertEquals(FlashcardError.EMPTY_ANSWER, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(Flashcard.class));
    }

    @Test
    void execute_WithAnswerTooLong_ShouldThrowException() {
        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute(validPregunta, "a".repeat(251))
        );
        assertEquals(FlashcardError.ANSWER_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(Flashcard.class));
    }
} 