package application.usecase.flashcard;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.usecase.flashcard.GetFlashcardUseCase;
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
    private DeckDTO testDeck;       // deck de prueba
    private UUID validDeckId;

    @BeforeEach
    void setUp() {
        getFlashcardUseCase = new GetFlashcardUseCase(flashcardService);

        // Armo un deck de prueba (para pasarlo al DTO)
        validDeckId = UUID.randomUUID();
        testDeck = new DeckDTO("Deck Test", "Desc Test");
        testDeck.setId(validDeckId);
    }

    @Test
    void execute_WithValidId_ShouldReturnFlashcard() {
        // GIVEN: un FlashcardDTO con deck asociado
        UUID flashId = UUID.randomUUID();
        FlashcardDTO mockFlashcardDTO = new FlashcardDTO(
                "¿Cuál es la capital de Francia?",
                "París",
                testDeck        // <-- le paso el deck aquí
        );
        mockFlashcardDTO.setId(flashId);

        // Configuro el service mock
        when(flashcardService.getFlashcardById(flashId)).thenReturn(mockFlashcardDTO);

        // WHEN: ejecuto el use case
        FlashcardDTO result = getFlashcardUseCase.execute(flashId);

        // THEN: lo devuelva correctamente
        assertNotNull(result);
        assertEquals(mockFlashcardDTO, result);
        verify(flashcardService, times(1)).getFlashcardById(flashId);
    }

    @Test
    void execute_WhenFlashcardNotFound_ShouldReturnNullAndPrintMessage() {
        // GIVEN: el service arroja excepción al no encontrar
        UUID flashId = UUID.randomUUID();
        when(flashcardService.getFlashcardById(flashId))
                .thenThrow(FlashcardError.flashcardNotFound());

        // WHEN
        FlashcardDTO result = getFlashcardUseCase.execute(flashId);

        // THEN: me devuelve null y llamó al service
        assertNull(result);
        verify(flashcardService, times(1)).getFlashcardById(flashId);
    }
}
