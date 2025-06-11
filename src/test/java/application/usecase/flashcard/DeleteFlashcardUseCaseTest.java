package application.usecase.flashcard;

import edu.utn.application.error.FlashcardError;
import edu.utn.application.usecase.flashcard.DeleteFlashcardUseCase;
import edu.utn.domain.service.flashcard.IFlashcardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteFlashcardUseCaseTest {

    @Mock
    private IFlashcardService flashcardService;

    private DeleteFlashcardUseCase deleteFlashcardUseCase;
    private UUID validFlashcardId;

    @BeforeEach
    void setUp() {
        deleteFlashcardUseCase = new DeleteFlashcardUseCase(flashcardService);
        validFlashcardId = UUID.randomUUID();
    }

    @Test
    void execute_WithValidFlashcardId_ShouldDeleteFlashcardAndReturnSuccessMessage() {
        doNothing().when(flashcardService).deleteFlashcard(validFlashcardId);

        String result = deleteFlashcardUseCase.execute(validFlashcardId);

        assertEquals("Flashcard borrada con éxito", result);
        verify(flashcardService, times(1)).deleteFlashcard(validFlashcardId);
    }

    @Test
    void execute_WithNullFlashcardId_ShouldThrowException() {
        FlashcardError exception = assertThrows(
                FlashcardError.class,
                () -> deleteFlashcardUseCase.execute(null)
        );
        assertEquals(FlashcardError.NULL_FLASHCARD_ID, exception.getMessage());
        verify(flashcardService, never()).deleteFlashcard(any(UUID.class));
    }

    @Test
    void execute_WhenDeleteThrowsException_ShouldReturnErrorMessage() {
        doThrow(new RuntimeException("Error inesperado")).when(flashcardService).deleteFlashcard(validFlashcardId);

        String result = deleteFlashcardUseCase.execute(validFlashcardId);

        assertEquals("Error al borrar flashcard: Error inesperado", result);
        verify(flashcardService, times(1)).deleteFlashcard(validFlashcardId);
    }
}
