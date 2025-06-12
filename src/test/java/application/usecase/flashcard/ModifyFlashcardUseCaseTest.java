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
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        doNothing().when(flashcardService).updateFlashcard(any(FlashcardDTO.class));

        FlashcardDTO modifiedFlashcard = new FlashcardDTO(
                validFlashcardId,
                "Pregunta modificada",
                null,
                existingFlashcard.getCreatedAt(),
                existingFlashcard.getNextReviewDate(),
                existingFlashcard.getUpdatedAt(),
                null,
                0
        );

        System.out.println("existingFlashcard pregunta: '" + existingFlashcard.getPregunta() + "'");
        System.out.println("DTO pregunta: '" + modifiedFlashcard.getPregunta() + "'");
        FlashcardDTO result = modifyFlashcardUseCase.execute(modifiedFlashcard);

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
            () -> modifyFlashcardUseCase.execute(null)
        );
        assertEquals(FlashcardError.NULL_FLASHCARD, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithNullFlashcardId_ShouldThrowException() {
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

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.NULL_FLASHCARD_ID, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithNoFieldsToModify_ShouldThrowException() {
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

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.NO_FIELDS_TO_MODIFY, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithNonExistentFlashcard_ShouldThrowException() {
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(null);

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(existingFlashcard)
        );
        assertEquals(FlashcardError.FLASHCARD_NOT_FOUND, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithEmptyQuestion_ShouldThrowException() {
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

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.EMPTY_QUESTION, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithQuestionTooShort_ShouldThrowException() {
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

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.QUESTION_TOO_SHORT, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithQuestionTooLong_ShouldThrowException() {
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

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.QUESTION_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithSameQuestion_ShouldThrowException() {
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

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.SAME_QUESTION, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithEmptyAnswer_ShouldThrowException() {
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        existingFlashcard.setPregunta("Pregunta anterior");
        existingFlashcard.setRespuesta("Respuesta anterior");
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

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.EMPTY_ANSWER, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithAnswerTooShort_ShouldThrowException() {
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        existingFlashcard.setPregunta("Pregunta antigua");
        existingFlashcard.setRespuesta("Respuesta antigua");
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

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.ANSWER_TOO_SHORT, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithAnswerTooLong_ShouldThrowException() {
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        existingFlashcard.setPregunta("Pregunta anterior");
        existingFlashcard.setRespuesta("Respuesta anterior");
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

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.ANSWER_TOO_LONG, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }

    @Test
    void execute_WithSameAnswer_ShouldThrowException() {
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(existingFlashcard);
        existingFlashcard.setPregunta("Pregunta anterior");
        existingFlashcard.setRespuesta("Respuesta actual");
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

        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> modifyFlashcardUseCase.execute(flashcardDTO)
        );
        assertEquals(FlashcardError.SAME_ANSWER, exception.getMessage());
        verify(flashcardService, never()).updateFlashcard(any(FlashcardDTO.class));
    }
} 