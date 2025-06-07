package application.usecase;

import edu.utn.application.error.FlashcardError;
import edu.utn.application.usecase.DeleteFlashcardUseCase;
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
class DeleteFlashcardUseCaseTest {

    @Mock
    private IFlashcardService flashcardService;

    @Mock
    private IFlashcard flashcard;

    private DeleteFlashcardUseCase deleteFlashcardUseCase;
    private UUID validFlashcardId;

    @BeforeEach
    void setUp() {
        deleteFlashcardUseCase = new DeleteFlashcardUseCase(flashcardService);
        validFlashcardId = UUID.randomUUID();
    }

    @Test
    void execute_WithValidFlashcardId_ShouldDeleteFlashcard() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(flashcard);
        doNothing().when(flashcardService).deleteFlashcard(validFlashcardId);

        // Act & Assert
        assertDoesNotThrow(() -> deleteFlashcardUseCase.execute(validFlashcardId));
        verify(flashcardService, times(1)).deleteFlashcard(validFlashcardId);
    }

    @Test
    void execute_WithNullFlashcardId_ShouldThrowException() {
        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> deleteFlashcardUseCase.execute(null)
        );
        assertEquals(FlashcardError.NULL_FLASHCARD_ID, exception.getMessage());
        verify(flashcardService, never()).deleteFlashcard(any(UUID.class));
    }

    @Test
    void execute_WithNonExistentFlashcard_ShouldThrowException() {
        // Arrange
        when(flashcardService.getFlashcardById(validFlashcardId)).thenReturn(null);

        // Act & Assert
        FlashcardError exception = assertThrows(
            FlashcardError.class,
            () -> deleteFlashcardUseCase.execute(validFlashcardId)
        );
        assertEquals(FlashcardError.FLASHCARD_NOT_FOUND, exception.getMessage());
        verify(flashcardService, never()).deleteFlashcard(any(UUID.class));
    }
} 