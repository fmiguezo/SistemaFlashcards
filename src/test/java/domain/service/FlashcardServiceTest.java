package domain.service;

import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.FlashcardService;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FlashcardServiceTest {

    private IFlashcardRepository flashcardRepository;
    private FlashcardService flashcardService;
    private IUserPracticeInputPort userPracticeInputPort;

    @BeforeEach
    void setUp() {
        flashcardRepository = mock(IFlashcardRepository.class);
        userPracticeInputPort = mock(IUserPracticeInputPort.class);
        flashcardService = new FlashcardService(flashcardRepository, userPracticeInputPort);
    }

    @Test
    void testAddFlashcard() {
        IFlashcard flashcard = mock(IFlashcard.class);

        flashcardService.addFlashcard(flashcard);

        verify(flashcardRepository).createCard(flashcard);
    }

    @Test
    void testGetFlashcardById() {
        UUID id = UUID.randomUUID();
        IFlashcard flashcard = mock(IFlashcard.class);
        when(flashcardRepository.getCardById(id)).thenReturn(flashcard);

        IFlashcard result = flashcardService.getFlashcardById(id);

        assertEquals(flashcard, result);
        verify(flashcardRepository).getCardById(id);
    }

    @Test
    void testUpdateFlashcard() {
        IFlashcard flashcard = mock(IFlashcard.class);

        flashcardService.updateFlashcard(flashcard);

        verify(flashcardRepository).updateCard(flashcard);
    }

    @Test
    void testDeleteFlashcard() {
        UUID id = UUID.randomUUID();

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