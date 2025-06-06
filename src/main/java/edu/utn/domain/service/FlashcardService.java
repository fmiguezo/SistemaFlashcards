package edu.utn.domain.service;
import edu.utn.domain.model.IEstrategiaRepeticion;
import edu.utn.domain.model.IFlashcard;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class FlashcardService implements IFlashcardService {
    private IFlashcardRepository flashcardRepository;
    private IUserPracticeInputPort userInputPort;

    public FlashcardService(IFlashcardRepository flashcardRepository, IUserPracticeInputPort userInput) {
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

    @Override
    public IFlashcardRepository getFlashcardRepository() {
        return flashcardRepository;
    }

    @Override
    public void setFlashcardRepository(IFlashcardRepository flashcardRepository) {
        this.flashcardRepository = flashcardRepository;
    }

    @Override
    public LocalDateTime calculateNextReviewDate(int score, IEstrategiaRepeticion estrategia) {
        return estrategia.calcularProximaRepeticion(score, LocalDateTime.now());
    }

    @Override
    public void practiceFlashcard(IFlashcard flashcard, IEstrategiaRepeticion estrategia) {
        if (userInputPort.isAnswerCorrect(flashcard)) {
            flashcard.setScore(flashcard.getScore() + 1);
        } else {
            flashcard.setScore(Math.max(0, flashcard.getScore() - 1));
        }
        flashcard.setNextReviewDate(calculateNextReviewDate(flashcard.getScore(), estrategia));
        flashcardRepository.updateCard(flashcard);
    }
}
