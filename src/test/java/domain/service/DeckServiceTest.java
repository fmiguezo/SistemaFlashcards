package domain.service;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.service.deck.DeckService;
import edu.utn.domain.service.flashcard.IFlashcardService;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DeckServiceTest {

    private IDeckRepository deckRepository;
    private IEstrategiaRepeticion estrategiaRepeticion;
    private DeckService deckService;
    private IFlashcardService flashcardService;
    private IFlashcardRepository flashcardRepository;

    @BeforeEach
    void setUp() {
        deckRepository = mock(IDeckRepository.class);
        estrategiaRepeticion = mock(IEstrategiaRepeticion.class);
        flashcardService = mock(IFlashcardService.class);
        flashcardRepository = mock(IFlashcardRepository.class);
        deckService = new DeckService(deckRepository, estrategiaRepeticion, flashcardService, flashcardRepository);
    }

    @Test
    void testAddDeck() {
        DeckDTO deckDTO = mock(DeckDTO.class);
        IDeck domainDeck = mock(IDeck.class);

        try (MockedStatic<DeckMapper> mockedMapper = Mockito.mockStatic(DeckMapper.class)) {
            mockedMapper.when(() -> DeckMapper.toDomain(deckDTO)).thenReturn(domainDeck);

            deckService.addDeck(deckDTO);

            mockedMapper.verify(() -> DeckMapper.toDomain(deckDTO));
            verify(deckRepository).createDeck(domainDeck);
        }
    }

    @Test
    void testGetDeckById() {
        UUID id = UUID.randomUUID();
        IDeck deck = mock(IDeck.class);
        when(deckRepository.getDeckById(id)).thenReturn(Optional.of(deck));

        DeckDTO dto = mock(DeckDTO.class);
        try (MockedStatic<DeckMapper> mocked = mockStatic(DeckMapper.class)) {
            mocked.when(() -> DeckMapper.toDTO(deck)).thenReturn(dto);
            DeckDTO result = deckService.getDeckById(id);
            assertEquals(dto, result);
            verify(deckRepository).getDeckById(id);
        }
    }

    @Test
    void testUpdateDeck() {
        DeckDTO deckDTO = mock(DeckDTO.class);
        IDeck domainDeck = mock(IDeck.class);

        try (MockedStatic<DeckMapper> mocked = mockStatic(DeckMapper.class)) {
            mocked.when(() -> DeckMapper.toDomain(deckDTO)).thenReturn(domainDeck);
            deckService.updateDeck(deckDTO);
            verify(deckRepository).updateDeck(domainDeck);
        }
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
        assertEquals("No se encontró el deck a modificar", exception.getMessage());
    }

    @Test
    void testGetAllDecks() {
        IDeck deck1 = mock(IDeck.class);
        IDeck deck2 = mock(IDeck.class);
        when(deckRepository.getAllDecks()).thenReturn(List.of(deck1, deck2));

        DeckDTO dto1 = mock(DeckDTO.class);
        DeckDTO dto2 = mock(DeckDTO.class);

        try (MockedStatic<DeckMapper> mocked = mockStatic(DeckMapper.class)) {
            mocked.when(() -> DeckMapper.toDTO(deck1)).thenReturn(dto1);
            mocked.when(() -> DeckMapper.toDTO(deck2)).thenReturn(dto2);
            List<DeckDTO> result = deckService.getAllDecks();
            assertEquals(List.of(dto1, dto2), result);
            verify(deckRepository).getAllDecks();
        }
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
        DeckDTO deck = mock(DeckDTO.class);
        IUserPracticeInputPort userInputPort = mock(IUserPracticeInputPort.class);

        FlashcardDTO card1 = mock(FlashcardDTO.class);
        FlashcardDTO card2 = mock(FlashcardDTO.class);

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