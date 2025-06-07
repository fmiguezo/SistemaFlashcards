package application.usecase;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.usecase.CreateFlashcardUseCase;
import edu.utn.domain.model.Flashcard;
import edu.utn.domain.service.IFlashcardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

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
        validFlashcardDTO = new FlashcardDTO(
            null,
            "¿Cuál es la capital de Francia?",
            "La capital de Francia es París",
            null,
            null,
            null,
            null,
            0
        );
    }

    @Test
    void execute_WithValidFlashcardDTO_ShouldCreateFlashcard() {
        // Arrange
        doNothing().when(flashcardService).addFlashcard(any(Flashcard.class));

        // Act
        FlashcardDTO result = createFlashcardUseCase.execute(validFlashcardDTO);

        // Assert
        assertNotNull(result);
        assertEquals(validFlashcardDTO.getPregunta(), result.getPregunta());
        assertEquals(validFlashcardDTO.getRespuesta(), result.getRespuesta());
        assertNotNull(result.getId());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertNotNull(result.getNextReviewDate());
        verify(flashcardService, times(1)).addFlashcard(any(Flashcard.class));
    }

    @Test
    void execute_WithNullFlashcardDTO_ShouldThrowException() {
        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute(null)
        );
        assertEquals(FlashcardError.NULL_FLASHCARD, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(Flashcard.class));
    }

    @Test
    void execute_WithNullQuestion_ShouldThrowException() {
        // Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            null,
            null,
            validFlashcardDTO.getRespuesta(),
            null,
            null,
            null,
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.EMPTY_QUESTION, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(Flashcard.class));
    }

    @Test
    void execute_WithEmptyQuestion_ShouldThrowException() {
        // Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            null,
            "",
            validFlashcardDTO.getRespuesta(),
            null,
            null,
            null,
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.EMPTY_QUESTION, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(Flashcard.class));
    }

    @Test
    void execute_WithQuestionTooLong_ShouldThrowException() {
        // Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            null,
            "a".repeat(101),
            validFlashcardDTO.getRespuesta(),
            null,
            null,
            null,
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.QUESTION_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(Flashcard.class));
    }

    @Test
    void execute_WithNullAnswer_ShouldThrowException() {
        // Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            null,
            validFlashcardDTO.getPregunta(),
            null,
            null,
            null,
            null,
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.EMPTY_ANSWER, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(Flashcard.class));
    }

    @Test
    void execute_WithEmptyAnswer_ShouldThrowException() {
        // Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            null,
            validFlashcardDTO.getPregunta(),
            "",
            null,
            null,
            null,
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.EMPTY_ANSWER, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(Flashcard.class));
    }

    @Test
    void execute_WithAnswerTooLong_ShouldThrowException() {
        // Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            null,
            validFlashcardDTO.getPregunta(),
            "a".repeat(251),
            null,
            null,
            null,
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> createFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.ANSWER_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).addFlashcard(any(Flashcard.class));
    }
} 