package application.usecase.flashcard;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.usecase.flashcard.GetFlashcardUseCase;
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
        UUID flashcardId = UUID.randomUUID();
        FlashcardDTO mockFlashcardDTO = new FlashcardDTO(
                flashcardId,
                "¿Cuál es la capital de Francia?",
                "París",
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                0
        );

        when(flashcardService.getFlashcardById(flashcardId)).thenReturn(mockFlashcardDTO);

        FlashcardDTO result = getFlashcardUseCase.execute(flashcardId);

        assertNotNull(result);
        assertEquals(mockFlashcardDTO, result);
        verify(flashcardService, times(1)).getFlashcardById(flashcardId);
    }

    @Test
    void execute_WhenFlashcardNotFound_ShouldReturnNullAndPrintMessage() {
        UUID flashcardId = UUID.randomUUID();
        when(flashcardService.getFlashcardById(flashcardId)).thenThrow(FlashcardError.flashcardNotFound());

        FlashcardDTO result = getFlashcardUseCase.execute(flashcardId);

        assertNull(result);
        verify(flashcardService, times(1)).getFlashcardById(flashcardId);
    }
}