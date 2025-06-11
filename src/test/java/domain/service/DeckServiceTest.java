package domain.service;

import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.domain.service.deck.DeckService;
import edu.utn.domain.service.flashcard.IFlashcardService;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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
        when(deckRepository.getDeckById(id)).thenReturn(Optional.of(deck));

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
        IDeck mockDeck = mock(IDeck.class);
        when(deckRepository.getDeckById(id)).thenReturn(Optional.of(mockDeck));

        deckService.deleteDeckById(id);

        verify(deckRepository).getDeckById(id);
        verify(deckRepository).deleteDeckById(id);
    }

    @Test
    void testDeleteDeck_WhenDeckNotFound_ShouldThrowException() {
        UUID id = UUID.randomUUID();
        when(deckRepository.getDeckById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> deckService.deleteDeckById(id));
        assertEquals("Deck no encontrado", exception.getMessage());
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
        IUserPracticeInputPort userInputPort = mock(IUserPracticeInputPort.class);

        IFlashcard card1 = mock(IFlashcard.class);
        IFlashcard card2 = mock(IFlashcard.class);

        when(deck.getFlashcards()).thenReturn(List.of(card1, card2));
        when(card1.getNextReviewDate()).thenReturn(LocalDateTime.now().minusDays(1));
        when(card1.getScore()).thenReturn(3);
        when(card2.getNextReviewDate()).thenReturn(LocalDateTime.now().minusDays(1));
        when(card2.getScore()).thenReturn(4);

        deckService.practiceDeck(deck, estrategiaRepeticion, userInputPort);

        verify(flashcardService).practiceFlashcard(card1, estrategiaRepeticion, userInputPort);
        verify(flashcardService).practiceFlashcard(card2, estrategiaRepeticion, userInputPort);
    }
}