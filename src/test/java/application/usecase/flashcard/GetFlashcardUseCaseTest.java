package application.usecase.flashcard;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.usecase.flashcard.GetFlashcardUseCase;
import edu.utn.domain.model.flashcard.IFlashcard;
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
class GetFlashcardUseCaseTest {

    @Mock
    private IFlashcardService flashcardService;

    private GetFlashcardUseCase getFlashcardUseCase;

    @BeforeEach
    void setUp() {
        getFlashcardUseCase = new GetFlashcardUseCase(flashcardService);
    }

    @Test
    void execute_WithValidId_ShouldReturnFlashcard() {
        // Arrange
        UUID flashcardId = UUID.randomUUID();
        IFlashcard mockFlashcard = mock(IFlashcard.class);
        when(flashcardService.getFlashcardById(flashcardId)).thenReturn(mockFlashcard);

        // Act
        IFlashcard result = getFlashcardUseCase.execute(flashcardId);

        // Assert
        assertNotNull(result);
        assertEquals(mockFlashcard, result);
        verify(flashcardService, times(1)).getFlashcardById(flashcardId);
    }

    @Test
    void execute_WhenFlashcardNotFound_ShouldReturnNullAndPrintMessage() {
        // Arrange
        UUID flashcardId = UUID.randomUUID();
        FlashcardError notFoundError = FlashcardError.flashcardNotFound();
        when(flashcardService.getFlashcardById(flashcardId)).thenThrow(notFoundError);

        // Act
        IFlashcard result = getFlashcardUseCase.execute(flashcardId);

        // Assert
        assertNull(result);
        verify(flashcardService, times(1)).getFlashcardById(flashcardId);
    }
}