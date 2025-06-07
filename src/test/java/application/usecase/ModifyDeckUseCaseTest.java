package application.usecase;

import edu.utn.application.error.DeckError;
import edu.utn.application.usecase.ModifyDeckUseCase;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.service.IDeckService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModifyDeckUseCaseTest {

    @Mock
    private IDeckService deckService;

    @Mock
    private IDeck deck;

    private ModifyDeckUseCase modifyDeckUseCase;
    private UUID validDeckId;
    private String validNombre;
    private String validDescripcion;
    private String existingNombre;
    private String existingDescripcion;

    @BeforeEach
    void setUp() {
        modifyDeckUseCase = new ModifyDeckUseCase(deckService);
        validDeckId = UUID.randomUUID();
        validNombre = "Deck de prueba";
        validDescripcion = "Descripción de prueba";
        existingNombre = "Deck existente";
        existingDescripcion = "Descripción existente";
    }

    @Test
    void execute_WithValidNameAndDescription_ShouldUpdateBoth() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(deck);
        when(deck.getNombre()).thenReturn(existingNombre);
        when(deck.getDescripcion()).thenReturn(existingDescripcion);
        doNothing().when(deckService).updateDeck(deck);

        // Act & Assert
        assertDoesNotThrow(() -> modifyDeckUseCase.execute(validDeckId, validNombre, validDescripcion));
        verify(deck, times(1)).setNombre(validNombre);
        verify(deck, times(1)).setDescripcion(validDescripcion);
        verify(deckService, times(1)).updateDeck(deck);
    }

    @Test
    void execute_WithOnlyName_ShouldUpdateOnlyName() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(deck);
        when(deck.getNombre()).thenReturn(existingNombre);
        doNothing().when(deckService).updateDeck(deck);

        // Act & Assert
        assertDoesNotThrow(() -> modifyDeckUseCase.execute(validDeckId, validNombre, null));
        verify(deck, times(1)).setNombre(validNombre);
        verify(deck, never()).setDescripcion(anyString());
        verify(deckService, times(1)).updateDeck(deck);
    }

    @Test
    void execute_WithOnlyDescription_ShouldUpdateOnlyDescription() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(deck);
        when(deck.getDescripcion()).thenReturn(existingDescripcion);
        doNothing().when(deckService).updateDeck(deck);

        // Act & Assert
        assertDoesNotThrow(() -> modifyDeckUseCase.execute(validDeckId, null, validDescripcion));
        verify(deck, never()).setNombre(anyString());
        verify(deck, times(1)).setDescripcion(validDescripcion);
        verify(deckService, times(1)).updateDeck(deck);
    }

    @Test
    void execute_WithEmptyDescription_ShouldUpdateDescription() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(deck);
        when(deck.getDescripcion()).thenReturn(existingDescripcion);
        doNothing().when(deckService).updateDeck(deck);

        // Act & Assert
        assertDoesNotThrow(() -> modifyDeckUseCase.execute(validDeckId, null, ""));
        verify(deck, never()).setNombre(anyString());
        verify(deck, times(1)).setDescripcion("");
        verify(deckService, times(1)).updateDeck(deck);
    }

    @Test
    void execute_WithNullDeckId_ShouldThrowException() {
        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(null, validNombre, validDescripcion)
        );
        assertEquals(DeckError.NULL_DECK_ID, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithNoFieldsToModify_ShouldThrowException() {
        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(validDeckId, null, null)
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
            () -> modifyDeckUseCase.execute(validDeckId, validNombre, validDescripcion)
        );
        assertEquals(DeckError.DECK_NOT_FOUND, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithEmptyName_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(deck);

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(validDeckId, "", validDescripcion)
        );
        assertEquals(DeckError.EMPTY_NAME, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithNameTooLong_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(deck);

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(validDeckId, "a".repeat(101), validDescripcion)
        );
        assertEquals(DeckError.NAME_TOO_LONG, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithDescriptionTooLong_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(deck);

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(validDeckId, validNombre, "a".repeat(251))
        );
        assertEquals(DeckError.DESCRIPTION_TOO_LONG, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithSameName_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(deck);
        when(deck.getNombre()).thenReturn(existingNombre);

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(validDeckId, existingNombre, validDescripcion)
        );
        assertEquals(DeckError.SAME_NAME, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }

    @Test
    void execute_WithSameDescription_ShouldThrowException() {
        // Arrange
        when(deckService.getDeckById(validDeckId)).thenReturn(deck);
        when(deck.getDescripcion()).thenReturn(existingDescripcion);

        // Act & Assert
        DeckError exception = assertThrows(
            DeckError.class,
            () -> modifyDeckUseCase.execute(validDeckId, validNombre, existingDescripcion)
        );
        assertEquals(DeckError.SAME_DESCRIPTION, exception.getMessage());
        verify(deckService, never()).updateDeck(any(IDeck.class));
    }
} 