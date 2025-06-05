package edu.utn.domain.service;
import edu.utn.domain.model.IFlashcard;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;

import java.util.UUID;

public interface IFlashcardService {
     void addFlashcard(IFlashcard flashcard);
     IFlashcard getFlashcardById(UUID id);
     void updateFlashcard(IFlashcard flashcard);
     void deleteFlashcard(UUID id);
     void addFlashcardToDeck(IFlashcard flashcard, UUID deckId);
     IFlashcardRepository getFlashcardRepository();
     void setFlashcardRepository(IFlashcardRepository flashcardRepository);
}
