package domain.service;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.error.FlashcardError;
import edu.utn.domain.service.validation.ValidationService;
import edu.utn.domain.service.flashcard.IFlashcardService;
import edu.utn.domain.service.deck.IDeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ValidationServiceTest {

    private ValidationService validationService;
    private FlashcardDTO validFlashcardDTO;
    private DeckDTO validDeckDTO;

    @BeforeEach
    void setUp() {
        validationService = new ValidationService();

        // **IMPORTANTE**: FlashcardDTO solo tiene un constructor que recibe (pregunta, respuesta, deck)
        // Como en este test no usamos el deck internamente, podemos pasarle un DeckDTO "vacío":
        DeckDTO dummyDeck = new DeckDTO("dummy", "dummy");
        validFlashcardDTO = new FlashcardDTO(
                "Pregunta válida",
                "Respuesta válida",
                dummyDeck
        );
        // Le seteamos un ID para que no rompa validaciones de "id null"
        validFlashcardDTO.setId(UUID.randomUUID());

        // DeckDTO sí tiene constructor (nombre, descripción):
        validDeckDTO = new DeckDTO(
                "Nombre válido",
                "Descripción válida"
        );
        validDeckDTO.setId(UUID.randomUUID());
    }

    @Test
    void validateFlashcardInput_valid() {
        assertDoesNotThrow(() -> validationService.validateFlashcardInput(validFlashcardDTO));
    }

    @Test
    void validateFlashcardInput_nullFlashcard() {
        FlashcardError ex = assertThrows(
                FlashcardError.class,
                () -> validationService.validateFlashcardInput(null)
        );
        assertEquals(FlashcardError.NULL_FLASHCARD, ex.getMessage());
    }

    @Test
    void validateFlashcardInput_emptyQuestion() {
        FlashcardDTO dto = new FlashcardDTO("", "Respuesta válida", validDeckDTO);
        FlashcardError ex = assertThrows(
                FlashcardError.class,
                () -> validationService.validateFlashcardInput(dto)
        );
        assertEquals(FlashcardError.EMPTY_QUESTION, ex.getMessage());
    }

    @Test
    void validateFlashcardInput_questionTooLong() {
        String longQuestion = "a".repeat(101);
        FlashcardDTO dto = new FlashcardDTO(longQuestion, "Respuesta válida", validDeckDTO);
        FlashcardError ex = assertThrows(
                FlashcardError.class,
                () -> validationService.validateFlashcardInput(dto)
        );
        assertEquals(FlashcardError.QUESTION_TOO_LONG, ex.getMessage());
    }

    @Test
    void validateFlashcardInput_emptyAnswer() {
        FlashcardDTO dto = new FlashcardDTO("Pregunta válida", "", validDeckDTO);
        FlashcardError ex = assertThrows(
                FlashcardError.class,
                () -> validationService.validateFlashcardInput(dto)
        );
        assertEquals(FlashcardError.EMPTY_ANSWER, ex.getMessage());
    }

    @Test
    void validateFlashcardInput_answerTooLong() {
        String longAnswer = "a".repeat(251);
        FlashcardDTO dto = new FlashcardDTO("Pregunta válida", longAnswer, validDeckDTO);
        FlashcardError ex = assertThrows(
                FlashcardError.class,
                () -> validationService.validateFlashcardInput(dto)
        );
        assertEquals(FlashcardError.ANSWER_TOO_LONG, ex.getMessage());
    }

    @Test
    void validateDeckInput_valid() {
        assertDoesNotThrow(() -> validationService.validateDeckInput(validDeckDTO));
    }

    @Test
    void validateDeckInput_nullDeck() {
        DeckError ex = assertThrows(
                DeckError.class,
                () -> validationService.validateDeckInput(null)
        );
        assertEquals(DeckError.NULL_DECK, ex.getMessage());
    }

    @Test
    void validateDeckInput_emptyName() {
        DeckDTO dto = new DeckDTO("", "desc");
        dto.setId(UUID.randomUUID());
        DeckError ex = assertThrows(
                DeckError.class,
                () -> validationService.validateDeckInput(dto)
        );
        assertEquals(DeckError.EMPTY_NAME, ex.getMessage());
    }

    @Test
    void validateDeckInput_nameTooLong() {
        String longName = "a".repeat(101);
        DeckDTO dto = new DeckDTO(longName, "desc");
        dto.setId(UUID.randomUUID());
        DeckError ex = assertThrows(
                DeckError.class,
                () -> validationService.validateDeckInput(dto)
        );
        assertEquals(DeckError.NAME_TOO_LONG, ex.getMessage());
    }

    @Test
    void validateDeckInput_descriptionTooLong() {
        String longDesc = "a".repeat(251);
        DeckDTO dto = new DeckDTO("nombre", longDesc);
        dto.setId(UUID.randomUUID());
        DeckError ex = assertThrows(
                DeckError.class,
                () -> validationService.validateDeckInput(dto)
        );
        assertEquals(DeckError.DESCRIPTION_TOO_LONG, ex.getMessage());
    }

    // **Resto de tests** de validación de modificación
    // Sólo asegúrate de pasar siempre un DeckDTO al FlashcardDTO en los mocks:

    @Test
    void validateFlashcardModification_valid() {
        IFlashcardService mockService = mock(IFlashcardService.class);
        // Para la modificación, la FlashcardDTO mock también requiere deck:
        FlashcardDTO toModify = new FlashcardDTO("Q","A", validDeckDTO);
        toModify.setId(validFlashcardDTO.getId());
        when(mockService.getFlashcardById(toModify.getId())).thenReturn(toModify);

        FlashcardDTO result = validationService.validateFlashcardModification(
                toModify, mockService, "Otra pregunta", "Otra respuesta"
        );
        assertNotNull(result);
    }

    // Los demás tests de modificación quedan igual, siempre inyectando un FlashcardDTO que tenga deck.
}
