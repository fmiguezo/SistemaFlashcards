package application.usecase;

import edu.utn.application.error.FlashcardError;
import edu.utn.application.usecase.ModifyFlashcardUseCase;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.IFlashcardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModifyFlashcardUseCaseTest {

    @Mock
    private IFlashcardService flashcardService;

    @Mock
    private IFlashcard flashcard;

    private ModifyFlashcardUseCase modifyFlashcardUseCase;
    private UUID validFlashcardId;
    private String validPregunta;
    private String validRespuesta;
    private String existingPregunta;
    private String existingRespuesta;

    @BeforeEach
    void setUp() {
        modifyFlashcardUseCase = new ModifyFlashcardUseCase(flashcardService);
        validFlashcardId = UUID.randomUUID();
        validPregunta = "¿Cuál es la capital de Francia?";
        validRespuesta = "La capital de Francia es París";
        existingPregunta = "¿Cuál es la capital de Italia?";
        existingRespuesta = "La capital de Italia es Roma";
    }

    @Test
    void execute_WithValidQuestionAndAnswer_ShouldUpdateBoth() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(flashcard);
        when(flashcard.getPregunta()).thenReturn(existingPregunta);
        when(flashcard.getRespuesta()).thenReturn(existingRespuesta);
        doNothing().when(flashcardService).updateFlashcard(flashcard);

        // Act & Assert
        assertDoesNotThrow(() -> modifyFlashcardUseCase.execute(validFlashcardId, validPregunta, validRespuesta));
        verify(flashcard, times(1)).setPregunta(validPregunta);
        verify(flashcard, times(1)).setRespuesta(validRespuesta);
        verify(flashcardService, times(1)).updateFlashcard(flashcard);
    }

    @Test
    void execute_WithOnlyQuestion_ShouldUpdateOnlyQuestion() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(flashcard);
        when(flashcard.getPregunta()).thenReturn(existingPregunta);
        doNothing().when(flashcardService).updateFlashcard(flashcard);

        // Act & Assert
        assertDoesNotThrow(() -> modifyFlashcardUseCase.execute(validFlashcardId, validPregunta, null));
        verify(flashcard, times(1)).setPregunta(validPregunta);
        verify(flashcard, never()).setRespuesta(anyString());
        verify(flashcardService, times(1)).updateFlashcard(flashcard);
    }

    @Test
    void execute_WithOnlyAnswer_ShouldUpdateOnlyAnswer() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(flashcard);
        when(flashcard.getRespuesta()).thenReturn(existingRespuesta);
        doNothing().when(flashcardService).updateFlashcard(flashcard);

        // Act & Assert
        assertDoesNotThrow(() -> modifyFlashcardUseCase.execute(validFlashcardId, null, validRespuesta));
        verify(flashcard, never()).setPregunta(anyString());
        verify(flashcard, times(1)).setRespuesta(validRespuesta);
        verify(flashcardService, times(1)).updateFlashcard(flashcard);
    }

    @Test
    void execute_WithNullFlashcardId_ShouldThrowException() {
        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(null, validPregunta, validRespuesta)
        );
        assertEquals(FlashcardError.NULL_FLASHCARD_ID, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithNoFieldsToModify_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> modifyFlashcardUseCase.execute(validFlashcardId, null, null)
        );
        assertEquals("Debe proporcionar al menos una pregunta o respuesta para modificar", exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithNonExistentFlashcard_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(null);

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(validFlashcardId, validPregunta, validRespuesta)
        );
        assertEquals(FlashcardError.FLASHCARD_NOT_FOUND, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithEmptyQuestion_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(flashcard);

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(validFlashcardId, "", validRespuesta)
        );
        assertEquals(FlashcardError.EMPTY_QUESTION, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithEmptyAnswer_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(flashcard);

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(validFlashcardId, validPregunta, "")
        );
        assertEquals(FlashcardError.EMPTY_ANSWER, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithQuestionTooShort_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(flashcard);

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(validFlashcardId, "Hola", validRespuesta)
        );
        assertEquals(FlashcardError.QUESTION_TOO_SHORT, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithAnswerTooShort_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(flashcard);

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(validFlashcardId, validPregunta, "Hola")
        );
        assertEquals(FlashcardError.ANSWER_TOO_SHORT, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithQuestionTooLong_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(flashcard);

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(validFlashcardId, "a".repeat(501), validRespuesta)
        );
        assertEquals(FlashcardError.QUESTION_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithAnswerTooLong_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(flashcard);

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(validFlashcardId, validPregunta, "a".repeat(501))
        );
        assertEquals(FlashcardError.ANSWER_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithSameQuestion_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(flashcard);
        when(flashcard.getPregunta()).thenReturn(existingPregunta);

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(validFlashcardId, existingPregunta, validRespuesta)
        );
        assertEquals(FlashcardError.SAME_QUESTION, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithSameAnswer_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(flashcard);
        when(flashcard.getRespuesta()).thenReturn(existingRespuesta);

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(validFlashcardId, validPregunta, existingRespuesta)
        );
        assertEquals(FlashcardError.SAME_ANSWER, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }
} 