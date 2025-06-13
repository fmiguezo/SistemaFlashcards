package edu.utn.domain.service.flashcard;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FlashcardService implements IFlashcardService {
    private IFlashcardRepository flashcardRepository;
    private IUserPracticeInputPort userInputPort;

    public FlashcardService(IFlashcardRepository flashcardRepository, IUserPracticeInputPort userInput) {
        this.flashcardRepository = flashcardRepository;
        this.userInputPort = userInput;
    }

    @Override
    public void addFlashcard(FlashcardDTO flashcardDTO) {
        IFlashcard flashcard = FlashcardMapper.toDomain(flashcardDTO);
        flashcardRepository.createCard(flashcard);
    }

    @Override
    public FlashcardDTO getFlashcardById(UUID id) {
        IFlashcard flashcard = flashcardRepository.getCardById(id)
                .orElseThrow(FlashcardError::flashcardNotFound);
        return FlashcardMapper.toDTO(flashcard);
    }

    @Override
    public void updateFlashcard(FlashcardDTO flashcardDTO) {
        IFlashcard flashcard = FlashcardMapper.toDomain(flashcardDTO);
        flashcardRepository.updateCard(flashcard);
    }

    @Override
    public void deleteFlashcard(UUID id) {
        getFlashcardById(id);
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
    public void practiceFlashcard(FlashcardDTO flashcardDTO, IEstrategiaRepeticion estrategia, IUserPracticeInputPort userInputPort) {
        IFlashcard flashcard = FlashcardMapper.toDomain(flashcardDTO);
        userInputPort.showQuestion(flashcard);
        userInputPort.showAnswer(flashcard);
        boolean answer = userInputPort.askUserForAnswer(flashcard);
        updateScore(flashcard, estrategia, answer);
    }

    @Override
    public void updateScore(IFlashcard flashcard, IEstrategiaRepeticion estrategia, boolean answer) {
        if (answer) {
            flashcard.setScore(flashcard.getScore() + 1);
        } else {
            flashcard.setScore(Math.max(0, flashcard.getScore() - 1));
        }
        flashcard.setNextReviewDate(calculateNextReviewDate(flashcard.getScore(), estrategia));
        flashcardRepository.updateCard(flashcard);
    }

    @Override
    public IUserPracticeInputPort getUserInputPort() {
        return userInputPort;
    }

    @Override
    public void setUserInputPort(IUserPracticeInputPort userInputPort) {
        this.userInputPort = userInputPort;
    }

}
