package edu.utn.domain.service;
import edu.utn.domain.model.IFlashcard;
import java.util.UUID;

public interface IFlashcardService {
     void addFlashcard(IFlashcard flashcard);
     IFlashcard getFlashcardById(UUID id);
     void updateFlashcard(IFlashcard flashcard);
     void deleteFlashcard(UUID id);
}
