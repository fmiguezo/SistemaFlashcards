package edu.utn.domain.service.deck;
import edu.utn.application.error.DeckError;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.domain.service.flashcard.IFlashcardService;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import edu.utn.infrastructure.ports.out.IDeckRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class DeckService implements IDeckService {
    private IDeckRepository deckRepository;
    private IEstrategiaRepeticion estrategiaRepeticion;
    private IFlashcardService flashcardService;

    public DeckService(IDeckRepository deckRepository, IEstrategiaRepeticion estrategiaRepeticion, IFlashcardService flashcardService) {
        this.deckRepository = deckRepository;
        this.estrategiaRepeticion = estrategiaRepeticion;
        this.flashcardService = flashcardService;
    }

    @Override
    public IDeckRepository getDeckRepository() {
        return deckRepository;
    }

    @Override
    public void setDeckRepository(IDeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @Override
    public IEstrategiaRepeticion getEstrategiaRepeticion() {
        return estrategiaRepeticion;
    }

    @Override
    public void setEstrategiaRepeticion(IEstrategiaRepeticion estrategiaRepeticion) {
        this.estrategiaRepeticion = estrategiaRepeticion;
    }

    @Override
    public void addDeck(IDeck deck) {
        deckRepository.createDeck(deck);
    }

    @Override
    public IDeck getDeckById(UUID id) {
        return deckRepository.getDeckById(id)
                .orElseThrow(DeckError::deckNotFound);
    }

    @Override
    public void updateDeck(IDeck deck) {
        deckRepository.updateDeck(deck);
    }

    @Override
    public void deleteDeckById(UUID id) {
        getDeckById(id);
        deckRepository.deleteDeckById(id);
    }

    @Override
    public List<IDeck> getAllDecks() {
        return deckRepository.getAllDecks();
    }

    @Override
    public List<IFlashcard> getFlashcardsToPractice(IDeck deck) {
        List<IFlashcard> flashcards = deck.getFlashcards();
        return flashcards.stream()
                .filter(flashcard -> flashcard.getNextReviewDate().isBefore(LocalDateTime.now()))
                .filter(flashcard -> flashcard.getScore() < 5)
                .sorted(Comparator.comparing(IFlashcard::getNextReviewDate))
                .limit(20)
                .toList();
    }


    @Override
    public void practiceDeck(IDeck deck, IEstrategiaRepeticion estrategia, IUserPracticeInputPort userInputPort) {
        List<IFlashcard> flashcardsToPractice = getFlashcardsToPractice(deck);
        for (IFlashcard flashcard : flashcardsToPractice) {
            flashcardService.practiceFlashcard(flashcard, estrategia,userInputPort);
        }
    }

    @Override
    public List<IFlashcard> getFlashcardsByDeckId(UUID id) {
        return deckRepository.getFlashcardsByDeckId(id);
    }
}
