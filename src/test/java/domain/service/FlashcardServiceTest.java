package domain.service;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.dto.DeckDTO;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.domain.service.flashcard.FlashcardService;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FlashcardServiceTest {

    private IFlashcardRepository flashcardRepository;
    private FlashcardService flashcardService;
    private IUserPracticeInputPort userPracticeInputPort;
    private IDeck mockDeck;

    @BeforeEach
    void setUp() {
        flashcardRepository = mock(IFlashcardRepository.class);
        userPracticeInputPort = mock(IUserPracticeInputPort.class);
        flashcardService = new FlashcardService(flashcardRepository, userPracticeInputPort);

        mockDeck = mock(IDeck.class);
    }

    @Test
    void testAddFlashcard() {
        FlashcardDTO flashcardDTO = mock(FlashcardDTO.class);
        IFlashcard flashcard = mock(IFlashcard.class);
        DeckDTO deckDTO = mock(DeckDTO.class);

        try (MockedStatic<FlashcardMapper> mapperMock = mockStatic(FlashcardMapper.class)) {
            mapperMock.when(() -> FlashcardMapper.toDomain(flashcardDTO, deckDTO)).thenReturn(flashcard);
            flashcardService.addFlashcard(flashcardDTO);
            verify(flashcardRepository).createCard(flashcard);
        }
    }

    @Test
    void testGetFlashcardById() {
        UUID id = UUID.randomUUID();
        IFlashcard flashcard = mock(IFlashcard.class);
        FlashcardDTO flashcardDTO = mock(FlashcardDTO.class);

        when(flashcardRepository.getCardById(id)).thenReturn(Optional.of(flashcard));

        try (MockedStatic<FlashcardMapper> mapperMock = mockStatic(FlashcardMapper.class)) {
            mapperMock.when(() -> FlashcardMapper.toDTO(flashcard, mockDeck)).thenReturn(flashcardDTO);

            FlashcardDTO result = flashcardService.getFlashcardById(id);

            assertEquals(flashcardDTO, result);
            verify(flashcardRepository).getCardById(id);
        }
    }

    @Test
    void testUpdateFlashcard() {
        FlashcardDTO flashcardDTO = mock(FlashcardDTO.class);
        IFlashcard flashcard = mock(IFlashcard.class);
        DeckDTO deckDTO = mock(DeckDTO.class);

        try (MockedStatic<FlashcardMapper> mapperMock = mockStatic(FlashcardMapper.class)) {
            mapperMock.when(() -> FlashcardMapper.toDomain(flashcardDTO, deckDTO)).thenReturn(flashcard);
            flashcardService.updateFlashcard(flashcardDTO);
            verify(flashcardRepository).updateCard(flashcard);
        }
    }

    @Test
    void testDeleteFlashcard() {
        UUID id = UUID.randomUUID();
        IFlashcard flashcardMock = mock(IFlashcard.class);
        when(flashcardRepository.getCardById(id)).thenReturn(Optional.of(flashcardMock));
        flashcardService.deleteFlashcard(id);
        verify(flashcardRepository).deleteCard(id);
    }

    @Test
    void testGettersYSetters() {
        IFlashcardRepository newRepository = mock(IFlashcardRepository.class);
        flashcardService.setFlashcardRepository(newRepository);
        assertEquals(newRepository, flashcardService.getFlashcardRepository());
    }
}
