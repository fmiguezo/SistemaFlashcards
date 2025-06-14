package edu.utn.domain.service.flashcard;
import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IFlashcardService {
    void addFlashcard(FlashcardDTO flashcardDTO);
    FlashcardDTO getFlashcardById(UUID id);
    void updateFlashcard(FlashcardDTO flashcardDTO);
    void deleteFlashcard(UUID id);
    IFlashcardRepository getFlashcardRepository();
    void setFlashcardRepository(IFlashcardRepository flashcardRepository);
    LocalDateTime calculateNextReviewDate(int score, IEstrategiaRepeticion estrategia);
    void practiceFlashcard(FlashcardDTO flashcard, IEstrategiaRepeticion estrategia, IUserPracticeInputPort userInputPort);
    void updateScore(IFlashcard flashcard, IEstrategiaRepeticion estrategia, boolean answer);
    IUserPracticeInputPort getUserInputPort();
    void setUserInputPort(IUserPracticeInputPort userInputPort);
    List<IFlashcard> getFlashcardsByDeckId(UUID deckId);
}
