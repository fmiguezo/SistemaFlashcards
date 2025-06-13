package edu.utn.domain.service.deck;
import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.error.DeckError;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.service.flashcard.IFlashcardService;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
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
    public void addDeck(DeckDTO deckDTO) {
        IDeck deck = DeckMapper.toDomain(deckDTO);
        deckRepository.createDeck(deck);
    }

    @Override
    public DeckDTO getDeckById(UUID id) {
        IDeck deck = deckRepository.getDeckById(id)
                .orElseThrow(DeckError::deckNotFound);
        return DeckMapper.toDTO(deck);
    }

    @Override
    public void updateDeck(DeckDTO deckDTO) {
        IDeck deck = DeckMapper.toDomain(deckDTO);
        deckRepository.updateDeck(deck);
    }

    @Override
    public void deleteDeckById(UUID id) {
        getDeckById(id);
        deckRepository.deleteDeckById(id);
    }

    @Override
    public List<DeckDTO> getAllDecks() {
        List<IDeck> decks = deckRepository.getAllDecks();
        return decks.stream()
                .map(DeckMapper::toDTO)
                .toList();
    }

    @Override
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
    public void practiceDeck(DeckDTO deck, IEstrategiaRepeticion estrategia, IUserPracticeInputPort userInputPort) {
        List<FlashcardDTO> flashcardsToPractice = getFlashcardsToPractice(deck);
        for (FlashcardDTO flashcard : flashcardsToPractice) {
            flashcardService.practiceFlashcard(flashcard, estrategia, userInputPort);
        }
    }

    @Override
    public List<FlashcardDTO> getFlashcardsByDeckId(UUID id) {
        return deckRepository.getFlashcardsByDeckId(id).stream()
                .map(FlashcardMapper::toDTO)
                .toList();
    }
}
