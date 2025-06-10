package edu.utn.domain.service;
import edu.utn.domain.model.IEstrategiaRepeticion;
import edu.utn.domain.model.IFlashcard;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import java.time.LocalDateTime;
import java.util.UUID;

public interface IFlashcardService {
    void addFlashcard(IFlashcard flashcard);
    IFlashcard getFlashcardById(UUID id);
    void updateFlashcard(IFlashcard flashcard);
    void deleteFlashcard(UUID id);
    IFlashcardRepository getFlashcardRepository();
    void setFlashcardRepository(IFlashcardRepository flashcardRepository);
    LocalDateTime calculateNextReviewDate(int score, IEstrategiaRepeticion estrategia);
    void practiceFlashcard(IFlashcard flashcard, IEstrategiaRepeticion estrategia, IUserPracticeInputPort userInputPort);
    void updateScore(IFlashcard flashcard, IEstrategiaRepeticion estrategia, boolean answer);
    IUserPracticeInputPort getUserInputPort();
    void setUserInputPort(IUserPracticeInputPort userInputPort);
}
