package edu.utn.domain.service.flashcard;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import edu.utn.infrastructure.ports.out.JpaFlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FlashcardService implements IFlashcardService {
    private IFlashcardRepository flashcardRepository;
    private IUserPracticeInputPort userInputPort;
    private IDeckRepository deckRepository;

    public FlashcardService(IFlashcardRepository flashcardRepository, IUserPracticeInputPort userInput, IDeckRepository deckRepository) {
        this.flashcardRepository = flashcardRepository;
        this.userInputPort = userInput;
        this.deckRepository = deckRepository;
    }

    @Override
    public void addFlashcard(FlashcardDTO flashcardDTO) {
        UUID deckId = flashcardDTO.getDeckID();

        IDeck deckDomain = deckRepository.getDeckById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck no encontrado con id: " + deckId));

        IFlashcard flashcard = FlashcardMapper.toDomain(flashcardDTO, deckDomain);
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
        UUID deckId = flashcardDTO.getDeckID();

        IDeck deckDomain = deckRepository.getDeckById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck no encontrado con id: " + deckId));

        IFlashcard flashcard = FlashcardMapper.toDomain(flashcardDTO, deckDomain);
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
        UUID deckId = flashcardDTO.getDeckID();

        IDeck deckDomain = deckRepository.getDeckById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck no encontrado con id: " + deckId));
        IFlashcard flashcard = FlashcardMapper.toDomain(flashcardDTO, deckDomain);
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

    @Override
    public List<IFlashcard> getFlashcardsByDeckId(UUID deckId) {
        return flashcardRepository.getFlashcardsByDeckId(deckId);
    }
}
