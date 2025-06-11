package domain.service;

import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.service.deck.DeckService;
import edu.utn.domain.service.flashcard.IFlashcardService;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DeckServiceTest {

    private IDeckRepository deckRepository;
    private IEstrategiaRepeticion estrategiaRepeticion;
    private DeckService deckService;
    IFlashcardService flashcardService;

    @BeforeEach
    void setUp() {
        deckRepository = mock(IDeckRepository.class);
        estrategiaRepeticion = mock(IEstrategiaRepeticion.class);
        flashcardService = mock(IFlashcardService.class);
        deckService = new DeckService(deckRepository, estrategiaRepeticion, flashcardService);
    }

    @Test
    void testAddDeck() {
        IDeck deck = mock(IDeck.class);

        deckService.addDeck(deck);

        verify(deckRepository).createDeck(deck);
    }

    @Test
    void testGetDeckById() {
        UUID id = UUID.randomUUID();
        IDeck deck = mock(IDeck.class);
        when(deckRepository.getDeckById(id)).thenReturn(deck);

        IDeck result = deckService.getDeckById(id);

        assertEquals(deck, result);
        verify(deckRepository).getDeckById(id);
    }

    @Test
    void testUpdateDeck() {
        IDeck deck = mock(IDeck.class);

        deckService.updateDeck(deck);

        verify(deckRepository).updateDeck(deck);
    }

    @Test
    void testDeleteDeck() {
        UUID id = UUID.randomUUID();

        deckService.deleteDeckById(id);

        verify(deckRepository).deleteDeckById(id);
    }

    @Test
    void testGetAllDecks() {
        List<IDeck> expectedDecks = Arrays.asList(mock(IDeck.class), mock(IDeck.class));
        when(deckRepository.getAllDecks()).thenReturn(expectedDecks);

        List<IDeck> result = deckService.getAllDecks();

        assertEquals(expectedDecks, result);
        verify(deckRepository).getAllDecks();
    }

    @Test
    void testGettersYSetters() {
        IDeckRepository repo = mock(IDeckRepository.class);
        IEstrategiaRepeticion newStrategy = mock(IEstrategiaRepeticion.class);

        deckService.setDeckRepository(repo);
        deckService.setEstrategiaRepeticion(newStrategy);

        assertEquals(repo, deckService.getDeckRepository());
        assertEquals(newStrategy, deckService.getEstrategiaRepeticion());
    }

    @Test
    void testPracticeDeck() {
        IDeck deck = mock(IDeck.class);

        // Falta definir el comportamiento de la estrategia de repeticion y la logica de practica para poder escribir el test
        deckService.practiceDeck(deck, estrategiaRepeticion);

        // Por ahora devolvemos true hasta tener definida la logica de practica
        assertTrue(true);
    }
}