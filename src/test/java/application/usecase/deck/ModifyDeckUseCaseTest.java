package application.usecase.deck;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.usecase.deck.ModifyDeckUseCase;
import edu.utn.domain.model.deck.Deck;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.service.deck.IDeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModifyDeckUseCaseTest {

    @Mock
    private IDeckService deckService;

    private ModifyDeckUseCase modifyDeckUseCase;
    private UUID validDeckId;
    private IDeck existingDeck;
    private DeckDTO validDeckDTO;

    @BeforeEach
    void setUp() {
        modifyDeckUseCase = new ModifyDeckUseCase(deckService);
        validDeckId = UUID.randomUUID();
        existingDeck = new Deck("Deck Original", "Descripción Original");
        validDeckDTO = new DeckDTO(validDeckId, "Nuevo Nombre", "Nueva Descripción", new ArrayList<>());
    }

    @Test
    void execute_WithValidDeckDTO_ShouldModifyDeck() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(existingDeck);
        doNothing().when(deckService).updateDeck(any(IDeck.class));

        // Act
        modifyDeckUseCase.execute(validDeckDTO);

        // Assert
        verify(deckService).updateDeck(argThat(deck -> 
            deck.getNombre().equals(validDeckDTO.getNombre()) &&
            deck.getDescripcion().equals(validDeckDTO.getDescripcion())
        ));
    }

    @Test
    void execute_WithNullDeckDTO_ShouldThrowException() {
        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(null)
        );
        assertEquals(DeckError.NULL_DECK, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithNullDeckId_ShouldThrowException() {
        // Arrange
        DeckDTO deckDTO = new DeckDTO(null, "Nuevo Nombre", "Nueva Descripción", new ArrayList<>());

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(deckDTO)
        );
        assertEquals(DeckError.NULL_DECK_ID, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithNoFieldsToModify_ShouldThrowException() {
        // Arrange
        DeckDTO deckDTO = new DeckDTO(validDeckId, null, null, new ArrayList<>());

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(deckDTO)
        );
        assertEquals(DeckError.NO_FIELDS_TO_MODIFY, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithNonExistentDeck_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(null);

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(validDeckDTO)
        );
        assertEquals(DeckError.DECK_NOT_FOUND, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithEmptyName_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(existingDeck);
        DeckDTO deckDTO = new DeckDTO(validDeckId, "", "Nueva Descripción", new ArrayList<>());

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(deckDTO)
        );
        assertEquals(DeckError.EMPTY_NAME, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithNameTooLong_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(existingDeck);
        DeckDTO deckDTO = new DeckDTO(validDeckId, "a".repeat(101), "Nueva Descripción", new ArrayList<>());

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(deckDTO)
        );
        assertEquals(DeckError.NAME_TOO_LONG, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithSameName_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(existingDeck);
        DeckDTO deckDTO = new DeckDTO(validDeckId, existingDeck.getNombre(), "Nueva Descripción", new ArrayList<>());

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(deckDTO)
        );
        assertEquals(DeckError.SAME_NAME, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithDescriptionTooLong_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(existingDeck);
        DeckDTO deckDTO = new DeckDTO(validDeckId, "Nuevo Nombre", "a".repeat(251), new ArrayList<>());

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(deckDTO)
        );
        assertEquals(DeckError.DESCRIPTION_TOO_LONG, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithSameDescription_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(existingDeck);
        DeckDTO deckDTO = new DeckDTO(validDeckId, "Nuevo Nombre", existingDeck.getDescripcion(), new ArrayList<>());

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(deckDTO)
        );
        assertEquals(DeckError.SAME_DESCRIPTION, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }
} 