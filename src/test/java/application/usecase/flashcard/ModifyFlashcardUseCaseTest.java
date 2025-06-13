package application.usecase.flashcard;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.usecase.flashcard.ModifyFlashcardUseCase;
import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.service.flashcard.IFlashcardService;
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
    private FlashcardDTO existingFlashcard;

    @BeforeEach
    void setUp() {
        modifyFlashcardUseCase = new ModifyFlashcardUseCase(flashcardService);
        validFlashcardId = UUID.randomUUID();
        existingFlashcard = new FlashcardDTO(
            "Pregunta original",
            "Respuesta original"
        );
        existingFlashcard.setId(validFlashcardId);
        existingFlashcard.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void execute_WithValidFlashcardDTO_ShouldModifyFlashcardAndReturnDTO() {
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        doNothing().when(flashcardService).updateFlashcard(any(FlashcardDTO.class));
        FlashcardDTO result = modifyFlashcardUseCase.execute(existingFlashcard, "Pregunta modificada", existingFlashcard.getRespuesta());

        verify(flashcardService).updateFlashcard(argThat(flashcard ->
                flashcard.getPregunta().equals("Pregunta modificada") &&
                        flashcard.getRespuesta().equals(existingFlashcard.getRespuesta())
        ));
        assertEquals("Pregunta modificada", result.getPregunta());
        assertEquals(existingFlashcard.getRespuesta(), result.getRespuesta());
    }

    @Test
    void execute_WithNullFlashcardDTO_ShouldThrowException() {
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(null, "pregunta", "respuesta")
        );
        assertEquals(FlashcardError.NULL_FLASHCARD, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithNoFieldsToModify_ShouldThrowException() {
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(existingFlashcard, null, null)
        );
        assertEquals(FlashcardError.NO_FIELDS_TO_MODIFY, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithNonExistentFlashcard_ShouldThrowException() {
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(null);

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(existingFlashcard, "Nueva Pregunta", "Nueva Respuesta")
        );
        assertEquals(FlashcardError.FLASHCARD_NOT_FOUND, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithEmptyQuestion_ShouldThrowException() {
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(existingFlashcard, "",
                    "Nueva Respuesta")
        );
        assertEquals(FlashcardError.EMPTY_QUESTION, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithQuestionTooShort_ShouldThrowException() {
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(existingFlashcard, "a".repeat(9),
                    "Nueva Respuesta")
        );
        assertEquals(FlashcardError.QUESTION_TOO_SHORT, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithQuestionTooLong_ShouldThrowException() {
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(existingFlashcard, "a".repeat(5001),
                    "Nueva Respuesta")
        );
        assertEquals(FlashcardError.QUESTION_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithEmptyAnswer_ShouldThrowException() {
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(existingFlashcard, "Nueva Pregunta",
                    "")
        );
        assertEquals(FlashcardError.EMPTY_ANSWER, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithAnswerTooShort_ShouldThrowException() {
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(existingFlashcard, "Nueva Pregunta",
                    "a".repeat(9))
        );
        assertEquals(FlashcardError.ANSWER_TOO_SHORT, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithAnswerTooLong_ShouldThrowException() {
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(existingFlashcard,"Nueva Pregunta",
                    "a".repeat(501))
        );
        assertEquals(FlashcardError.ANSWER_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }


} 