package edu.utn.domain.service;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.model.IEstrategiaRepeticion;
import edu.utn.domain.model.IFlashcard;
import edu.utn.infrastructure.ports.out.IDeckRepository;

import java.time.LocalDateTime;
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
        return deckRepository.getDeckById(id);
    }

    @Override
    public void updateDeck(IDeck deck) {
        deckRepository.updateDeck(deck);
    }

    @Override
    public void deleteDeck(UUID id) {
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
                .toList();
    }

    @Override
    public void practiceDeck(IDeck deck, IEstrategiaRepeticion estrategia) {
        List<IFlashcard> flashcardsToPractice = getFlashcardsToPractice(deck);
        for (IFlashcard flashcard : flashcardsToPractice) {
            flashcardService.practiceFlashcard(flashcard, estrategia);
        }
    }

    @Override
    public List<IFlashcard> getFlashcardsByDeckId(UUID deckId) {
        return deckRepository.getFlashcardsByDeckId(deckId);
    }
}
