package edu.utn.domain.service;
import edu.utn.domain.model.IFlashcard;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;

import java.util.UUID;

public class FlashcardService implements IFlashcardService{
    private IFlashcardRepository flashcardRepository;

    public FlashcardService(IFlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    @Override
    public void addFlashcard(IFlashcard flashcard) {
        flashcardRepository.createCard(flashcard);
    }

    @Override
    public IFlashcard getFlashcardById(UUID id) {
        return flashcardRepository.getCardById(id);
    }

    @Override
    public void updateFlashcard(IFlashcard flashcard) {
        flashcardRepository.updateCard(flashcard);
    }

    @Override
    public void deleteFlashcard(UUID id) {
        flashcardRepository.deleteCard(id);
    }

    public IFlashcardRepository getFlashcardRepository() {
        return flashcardRepository;
    }

    public void setFlashcardRepository(IFlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }
}
