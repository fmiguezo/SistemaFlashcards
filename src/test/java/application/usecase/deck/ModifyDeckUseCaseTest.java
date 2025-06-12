package application.usecase.deck;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.usecase.deck.ModifyDeckUseCase;
import edu.utn.domain.model.deck.Deck;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.service.deck.IDeckService;
import edu.utn.domain.service.validation.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModifyDeckUseCaseTest {

    @Mock
    private IDeckService deckService;

    @InjectMocks
    private ModifyDeckUseCase modifyDeckUseCase;

    private ValidationService validationServiceSpy;

    private DeckDTO existingDeckDTO;

    @BeforeEach
    void setUp() {
        existingDeckDTO = new DeckDTO(
                UUID.randomUUID(),
                "Nombre Original",
                "Descripción Original",
                new ArrayList<>()
        );

        validationServiceSpy = Mockito.spy(new ValidationService());

        try {
            Field field = ModifyDeckUseCase.class.getDeclaredField("validationService");
            field.setAccessible(true);
            field.set(modifyDeckUseCase, validationServiceSpy);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void execute_WithValidNameAndDescription_ShouldUpdateDeck() {
        DeckDTO modification = new DeckDTO(
                existingDeckDTO.getId(),
                "Nuevo Nombre",
                "Nueva Descripción",
                new ArrayList<>()
        );

        doReturn(existingDeckDTO).when(validationServiceSpy).validateDeckModification(modification, deckService);

        DeckDTO result = modifyDeckUseCase.execute(modification);

        assertNotNull(result);
        assertEquals("Nuevo Nombre", result.getNombre());
        assertEquals("Nueva Descripción", result.getDescripcion());

        verify(deckService, times(1)).updateDeck(result);
        verify(validationServiceSpy, times(1)).validateDeckModification(modification, deckService);
    }

    @Test
    void execute_WithOnlyName_ShouldUpdateNameOnly() {
        DeckDTO modification = new DeckDTO(
                existingDeckDTO.getId(),
                "Nombre Cambiado",
                null,
                new ArrayList<>()
        );

        doReturn(existingDeckDTO).when(validationServiceSpy).validateDeckModification(modification, deckService);

        DeckDTO result = modifyDeckUseCase.execute(modification);

        assertEquals("Nombre Cambiado", result.getNombre());
        assertEquals(existingDeckDTO.getDescripcion(), result.getDescripcion());

        verify(deckService).updateDeck(result);
    }

    @Test
    void execute_WithOnlyDescription_ShouldUpdateDescriptionOnly() {
        DeckDTO modification = new DeckDTO(
                existingDeckDTO.getId(),
                null,
                "Descripción Cambiada",
                new ArrayList<>()
        );

        doReturn(existingDeckDTO).when(validationServiceSpy).validateDeckModification(modification, deckService);

        DeckDTO result = modifyDeckUseCase.execute(modification);

        assertEquals(existingDeckDTO.getNombre(), result.getNombre());
        assertEquals("Descripción Cambiada", result.getDescripcion());

        verify(deckService).updateDeck(result);
    }

    @Test
    void execute_WhenValidationFails_ShouldThrowDeckError() {
        DeckDTO modification = new DeckDTO(
                existingDeckDTO.getId(),
                "Nombre inválido",
                "Descripción inválida",
                new ArrayList<>()
        );

        doThrow(new DeckError("Error de validación")).when(validationServiceSpy)
                .validateDeckModification(modification, deckService);

        DeckError ex = assertThrows(DeckError.class, () -> modifyDeckUseCase.execute(modification));

        assertEquals("Error de validación", ex.getMessage());
        verify(deckService, never()).updateDeck(any());
    }
}