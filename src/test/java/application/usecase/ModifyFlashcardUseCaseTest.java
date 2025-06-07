package application.usecase;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.usecase.ModifyFlashcardUseCase;
import edu.utn.domain.model.Flashcard;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.IFlashcardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModifyFlashcardUseCaseTest {

    @Mock
    private IFlashcardService flashcardService;

    private ModifyFlashcardUseCase modifyFlashcardUseCase;
    private UUID validFlashcardId;
    private IFlashcard existingFlashcard;
    private FlashcardDTO validFlashcardDTO;

    @BeforeEach
    void setUp() {
        modifyFlashcardUseCase = new ModifyFlashcardUseCase(flashcardService);
        validFlashcardId = UUID.randomUUID();
        existingFlashcard = new Flashcard("Pregunta Original", "Respuesta Original");
        validFlashcardDTO = new FlashcardDTO(
            validFlashcardId,
            "Nueva Pregunta",
            "Nueva Respuesta",
            LocalDateTime.now(),
            null,
            LocalDateTime.now(),
            null,
            0
        );
    }

    @Test
    void execute_WithValidFlashcardDTO_ShouldModifyFlashcardAndReturnDTO() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        doNothing().when(flashcardService).updateFlashcard(any(IFlashcard.class));

        // Act
        FlashcardDTO result = modifyFlashcardUseCase.execute(validFlashcardDTO);

        // Assert
        verify(flashcardService).updateFlashcard(argThat(flashcard -> 
            flashcard.getPregunta().equals(validFlashcardDTO.getPregunta()) &&
            flashcard.getRespuesta().equals(validFlashcardDTO.getRespuesta())
        ));
        assertEquals(validFlashcardDTO.getPregunta(), result.getPregunta());
        assertEquals(validFlashcardDTO.getRespuesta(), result.getRespuesta());
    }

    @Test
    void execute_WithNullFlashcardDTO_ShouldThrowException() {
        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(null)
        );
        assertEquals(FlashcardError.NULL_FLASHCARD, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithNullFlashcardId_ShouldThrowException() {
        // Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            null,
            "Nueva Pregunta",
            "Nueva Respuesta",
            LocalDateTime.now(),
            null,
            LocalDateTime.now(),
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.NULL_FLASHCARD_ID, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithNoFieldsToModify_ShouldThrowException() {
        // Arrange
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            validFlashcardId,
            null,
            null,
            LocalDateTime.now(),
            null,
            LocalDateTime.now(),
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.NO_FIELDS_TO_MODIFY, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithNonExistentFlashcard_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(null);

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(validFlashcardDTO)
        );
        assertEquals(FlashcardError.FLASHCARD_NOT_FOUND, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithEmptyQuestion_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            validFlashcardId,
            "",
            "Nueva Respuesta",
            LocalDateTime.now(),
            null,
            LocalDateTime.now(),
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.EMPTY_QUESTION, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithQuestionTooShort_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            validFlashcardId,
            "a".repeat(9),
            "Nueva Respuesta",
            LocalDateTime.now(),
            null,
            LocalDateTime.now(),
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.QUESTION_TOO_SHORT, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithQuestionTooLong_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            validFlashcardId,
            "a".repeat(501),
            "Nueva Respuesta",
            LocalDateTime.now(),
            null,
            LocalDateTime.now(),
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.QUESTION_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithSameQuestion_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            validFlashcardId,
            existingFlashcard.getPregunta(),
            "Nueva Respuesta",
            LocalDateTime.now(),
            null,
            LocalDateTime.now(),
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.SAME_QUESTION, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithEmptyAnswer_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            validFlashcardId,
            "Nueva Pregunta",
            "",
            LocalDateTime.now(),
            null,
            LocalDateTime.now(),
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.EMPTY_ANSWER, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithAnswerTooShort_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            validFlashcardId,
            "Nueva Pregunta",
            "a".repeat(9),
            LocalDateTime.now(),
            null,
            LocalDateTime.now(),
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.ANSWER_TOO_SHORT, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithAnswerTooLong_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            validFlashcardId,
            "Nueva Pregunta",
            "a".repeat(501),
            LocalDateTime.now(),
            null,
            LocalDateTime.now(),
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.ANSWER_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }

    @Test
    void execute_WithSameAnswer_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        FlashcardDTO flashcardDTO = new FlashcardDTO(
            validFlashcardId,
            "Nueva Pregunta",
            existingFlashcard.getRespuesta(),
            LocalDateTime.now(),
            null,
            LocalDateTime.now(),
            null,
            0
        );

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.SAME_ANSWER, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(IFlashcard.class));
    }
} 