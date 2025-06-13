package edu.utn.domain.service.deck;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.domain.service.flashcard.IFlashcardService;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeckService implements IDeckService {
    private IDeckRepository deckRepository;
    private IEstrategiaRepeticion estrategiaRepeticion;
    private IFlashcardService flashcardService;
    private IFlashcardRepository flashcardRepository;

    public DeckService(IDeckRepository deckRepository, IEstrategiaRepeticion estrategiaRepeticion, IFlashcardService flashcardService, IFlashcardRepository flashcardRepository) {
        this.deckRepository = deckRepository;
        this.estrategiaRepeticion = estrategiaRepeticion;
        this.flashcardService = flashcardService;
        this.flashcardRepository = flashcardRepository;
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
    public void addDeck(DeckDTO deckDTO) {
        IDeck deck = DeckMapper.toDomain(deckDTO);
        deckRepository.createDeck(deck);
    }

    @Override
    @Transactional
    public List<FlashcardDTO> getFlashcardsByDeckId(UUID deckId) {
        return flashcardRepository.getFlashcardsByDeckId(deckId)
                .stream()
                .map(FlashcardMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateDeck(DeckDTO deckDTO) {
        IDeck deck = DeckMapper.toDomain(deckDTO);
        deckRepository.updateDeck(deck);
    }

    @Override
    public void deleteDeckById(UUID id) {
        deckRepository.deleteDeckById(id);
    }

    @Override
    @Transactional
    public List<DeckDTO> getAllDecks() {
        List<IDeck> decks = deckRepository.getAllDecks();
        return decks.stream()
                .map(DeckMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public List<FlashcardDTO> getFlashcardsToPractice(DeckDTO deck) {
        List<FlashcardDTO> flashcards = deck.getFlashcards();
        return flashcards.stream()
                .filter(flashcard -> flashcard.getNextReviewDate().isBefore(LocalDateTime.now()))
                .filter(flashcard -> flashcard.getScore() < 5)
                .sorted(Comparator.comparing(FlashcardDTO::getNextReviewDate))
                .limit(20)
                .toList();
    }

    @Override
    public DeckDTO getDeckById(UUID deckId) {
        IDeck deck = deckRepository.getDeckById(deckId)
                .orElseThrow(() -> new RuntimeException("Deck no encontrado con id: " + deckId));
        return DeckMapper.toDTO(deck);
    }


    @Override
    @Transactional
    public void practiceDeck(DeckDTO deck, IEstrategiaRepeticion estrategia, IUserPracticeInputPort userInputPort) {
        List<FlashcardDTO> flashcardsToPractice = getFlashcardsToPractice(deck);
        for (FlashcardDTO flashcard : flashcardsToPractice) {
            flashcardService.practiceFlashcard(flashcard, estrategia, userInputPort);
        }
    }
}
