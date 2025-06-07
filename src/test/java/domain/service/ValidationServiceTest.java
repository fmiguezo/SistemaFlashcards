package domain.service;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.error.FlashcardError;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.ValidationService;
import edu.utn.domain.service.IFlashcardService;
import edu.utn.domain.service.IDeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        validFlashcardDTO = new FlashcardDTO(
            UUID.randomUUID(),
            "Pregunta válida",
            "Respuesta válida",
            null, null, null, null, 0
        );
        validDeckDTO = new DeckDTO(
            UUID.randomUUID(),
            "Nombre válido",
            "Descripción válida",
            null
        );
    }

    // Flashcard input
    @Test
    void validateFlashcardInput_valid() {
        assertDoesNotThrow(() -> validationService.validateFlashcardInput(validFlashcardDTO));
    }

    @Test
    void validateFlashcardInput_nullFlashcard() {
        FlashcardError ex = assertThrows(FlashcardError.class, () -> validationService.validateFlashcardInput(null));
        assertEquals(FlashcardError.NULL_FLASHCARD, ex.getMessage());
    }

    @Test
    void validateFlashcardInput_emptyQuestion() {
        FlashcardDTO dto = new FlashcardDTO(null, "", "Respuesta", null, null, null, null, 0);
        FlashcardError ex = assertThrows(FlashcardError.class, () -> validationService.validateFlashcardInput(dto));
        assertEquals(FlashcardError.EMPTY_QUESTION, ex.getMessage());
    }

    @Test
    void validateFlashcardInput_questionTooLong() {
        String longQuestion = "a".repeat(101);
        FlashcardDTO dto = new FlashcardDTO(null, longQuestion, "Respuesta", null, null, null, null, 0);
        FlashcardError ex = assertThrows(FlashcardError.class, () -> validationService.validateFlashcardInput(dto));
        assertEquals(FlashcardError.QUESTION_TOO_LONG, ex.getMessage());
    }

    @Test
    void validateFlashcardInput_emptyAnswer() {
        FlashcardDTO dto = new FlashcardDTO(null, "Pregunta", "", null, null, null, null, 0);
        FlashcardError ex = assertThrows(FlashcardError.class, () -> validationService.validateFlashcardInput(dto));
        assertEquals(FlashcardError.EMPTY_ANSWER, ex.getMessage());
    }

    @Test
    void validateFlashcardInput_answerTooLong() {
        String longAnswer = "a".repeat(251);
        FlashcardDTO dto = new FlashcardDTO(null, "Pregunta", longAnswer, null, null, null, null, 0);
        FlashcardError ex = assertThrows(FlashcardError.class, () -> validationService.validateFlashcardInput(dto));
        assertEquals(FlashcardError.ANSWER_TOO_LONG, ex.getMessage());
    }

    // Deck input
    @Test
    void validateDeckInput_valid() {
        assertDoesNotThrow(() -> validationService.validateDeckInput(validDeckDTO));
    }

    @Test
    void validateDeckInput_nullDeck() {
        DeckError ex = assertThrows(DeckError.class, () -> validationService.validateDeckInput(null));
        assertEquals(DeckError.NULL_DECK, ex.getMessage());
    }

    @Test
    void validateDeckInput_emptyName() {
        DeckDTO dto = new DeckDTO(null, "", "desc", null);
        DeckError ex = assertThrows(DeckError.class, () -> validationService.validateDeckInput(dto));
        assertEquals(DeckError.EMPTY_NAME, ex.getMessage());
    }

    @Test
    void validateDeckInput_nameTooLong() {
        String longName = "a".repeat(101);
        DeckDTO dto = new DeckDTO(null, longName, "desc", null);
        DeckError ex = assertThrows(DeckError.class, () -> validationService.validateDeckInput(dto));
        assertEquals(DeckError.NAME_TOO_LONG, ex.getMessage());
    }

    @Test
    void validateDeckInput_descriptionTooLong() {
        String longDesc = "a".repeat(251);
        DeckDTO dto = new DeckDTO(null, "nombre", longDesc, null);
        DeckError ex = assertThrows(DeckError.class, () -> validationService.validateDeckInput(dto));
        assertEquals(DeckError.DESCRIPTION_TOO_LONG, ex.getMessage());
    }

    // Flashcard modification
    @Test
    void validateFlashcardModification_valid() {
        IFlashcardService mockService = mock(IFlashcardService.class);
        IFlashcard mockFlashcard = mock(IFlashcard.class);
        when(mockService.getFlashcardById(validFlashcardDTO.getId())).thenReturn(mockFlashcard);
        when(mockFlashcard.getPregunta()).thenReturn("Pregunta anterior");
        when(mockFlashcard.getRespuesta()).thenReturn("Respuesta anterior");

        IFlashcard result = validationService.validateFlashcardModification(validFlashcardDTO, mockService);
        assertEquals(mockFlashcard, result);
    }

    @Test
    void validateFlashcardModification_nullFlashcard() {
        IFlashcardService mockService = mock(IFlashcardService.class);
        FlashcardError ex = assertThrows(FlashcardError.class, () -> validationService.validateFlashcardModification(null, mockService));
        assertEquals(FlashcardError.NULL_FLASHCARD, ex.getMessage());
    }

    @Test
    void validateFlashcardModification_nullId() {
        IFlashcardService mockService = mock(IFlashcardService.class);
        FlashcardDTO dto = new FlashcardDTO(null, "Pregunta", "Respuesta", null, null, null, null, 0);
        FlashcardError ex = assertThrows(FlashcardError.class, () -> validationService.validateFlashcardModification(dto, mockService));
        assertEquals(FlashcardError.NULL_FLASHCARD_ID, ex.getMessage());
    }

    @Test
    void validateFlashcardModification_noFieldsToModify() {
        IFlashcardService mockService = mock(IFlashcardService.class);
        FlashcardDTO dto = new FlashcardDTO(UUID.randomUUID(), null, null, null, null, null, null, 0);
        FlashcardError ex = assertThrows(FlashcardError.class, () -> validationService.validateFlashcardModification(dto, mockService));
        assertEquals(FlashcardError.NO_FIELDS_TO_MODIFY, ex.getMessage());
    }

    @Test
    void validateFlashcardModification_flashcardNotFound() {
        IFlashcardService mockService = mock(IFlashcardService.class);
        when(mockService.getFlashcardById(validFlashcardDTO.getId())).thenReturn(null);
        FlashcardError ex = assertThrows(FlashcardError.class, () -> validationService.validateFlashcardModification(validFlashcardDTO, mockService));
        assertEquals(FlashcardError.FLASHCARD_NOT_FOUND, ex.getMessage());
    }

    @Test
    void validateFlashcardModification_sameQuestion() {
        IFlashcardService mockService = mock(IFlashcardService.class);
        IFlashcard mockFlashcard = mock(IFlashcard.class);
        when(mockService.getFlashcardById(validFlashcardDTO.getId())).thenReturn(mockFlashcard);
        when(mockFlashcard.getPregunta()).thenReturn(validFlashcardDTO.getPregunta());
        when(mockFlashcard.getRespuesta()).thenReturn("Respuesta anterior");

        FlashcardError ex = assertThrows(FlashcardError.class, () -> validationService.validateFlashcardModification(validFlashcardDTO, mockService));
        assertEquals(FlashcardError.SAME_QUESTION, ex.getMessage());
    }

    @Test
    void validateFlashcardModification_sameAnswer() {
        IFlashcardService mockService = mock(IFlashcardService.class);
        IFlashcard mockFlashcard = mock(IFlashcard.class);
        when(mockService.getFlashcardById(validFlashcardDTO.getId())).thenReturn(mockFlashcard);
        when(mockFlashcard.getPregunta()).thenReturn("Pregunta anterior");
        when(mockFlashcard.getRespuesta()).thenReturn(validFlashcardDTO.getRespuesta());

        FlashcardError ex = assertThrows(FlashcardError.class, () -> validationService.validateFlashcardModification(validFlashcardDTO, mockService));
        assertEquals(FlashcardError.SAME_ANSWER, ex.getMessage());
    }

    // Deck modification
    @Test
    void validateDeckModification_valid() {
        IDeckService mockService = mock(IDeckService.class);
        IDeck mockDeck = mock(IDeck.class);
        when(mockService.getDeckById(validDeckDTO.getId())).thenReturn(mockDeck);
        when(mockDeck.getNombre()).thenReturn("Nombre anterior");
        when(mockDeck.getDescripcion()).thenReturn("Descripción anterior");

        IDeck result = validationService.validateDeckModification(validDeckDTO, mockService);
        assertEquals(mockDeck, result);
    }

    @Test
    void validateDeckModification_nullDeck() {
        IDeckService mockService = mock(IDeckService.class);
        DeckError ex = assertThrows(DeckError.class, () -> validationService.validateDeckModification(null, mockService));
        assertEquals(DeckError.NULL_DECK, ex.getMessage());
    }

    @Test
    void validateDeckModification_nullId() {
        IDeckService mockService = mock(IDeckService.class);
        DeckDTO dto = new DeckDTO(null, "Nombre", "Descripción", null);
        DeckError ex = assertThrows(DeckError.class, () -> validationService.validateDeckModification(dto, mockService));
        assertEquals(DeckError.NULL_DECK_ID, ex.getMessage());
    }

    @Test
    void validateDeckModification_noFieldsToModify() {
        IDeckService mockService = mock(IDeckService.class);
        DeckDTO dto = new DeckDTO(UUID.randomUUID(), null, null, null);
        DeckError ex = assertThrows(DeckError.class, () -> validationService.validateDeckModification(dto, mockService));
        assertEquals(DeckError.NO_FIELDS_TO_MODIFY, ex.getMessage());
    }

    @Test
    void validateDeckModification_deckNotFound() {
        IDeckService mockService = mock(IDeckService.class);
        when(mockService.getDeckById(validDeckDTO.getId())).thenReturn(null);
        DeckError ex = assertThrows(DeckError.class, () -> validationService.validateDeckModification(validDeckDTO, mockService));
        assertEquals(DeckError.DECK_NOT_FOUND, ex.getMessage());
    }

    @Test
    void validateDeckModification_sameName() {
        IDeckService mockService = mock(IDeckService.class);
        IDeck mockDeck = mock(IDeck.class);
        when(mockService.getDeckById(validDeckDTO.getId())).thenReturn(mockDeck);
        when(mockDeck.getNombre()).thenReturn(validDeckDTO.getNombre());
        when(mockDeck.getDescripcion()).thenReturn("Descripción anterior");

        DeckError ex = assertThrows(DeckError.class, () -> validationService.validateDeckModification(validDeckDTO, mockService));
        assertEquals(DeckError.SAME_NAME, ex.getMessage());
    }

    @Test
    void validateDeckModification_sameDescription() {
        IDeckService mockService = mock(IDeckService.class);
        IDeck mockDeck = mock(IDeck.class);
        when(mockService.getDeckById(validDeckDTO.getId())).thenReturn(mockDeck);
        when(mockDeck.getNombre()).thenReturn("Nombre anterior");
        when(mockDeck.getDescripcion()).thenReturn(validDeckDTO.getDescripcion());

        DeckError ex = assertThrows(DeckError.class, () -> validationService.validateDeckModification(validDeckDTO, mockService));
        assertEquals(DeckError.SAME_DESCRIPTION, ex.getMessage());
    }
} 