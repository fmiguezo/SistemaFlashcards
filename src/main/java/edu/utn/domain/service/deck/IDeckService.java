package edu.utn.domain.service.deck;
import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import edu.utn.infrastructure.ports.out.IDeckRepository;

import java.util.List;
import java.util.UUID;

public interface IDeckService {
    void addDeck(DeckDTO deckDTO);
    DeckDTO getDeckById(UUID id);
    void updateDeck(DeckDTO deck);
    void deleteDeckById(UUID id);
    List<DeckDTO> getAllDecks();
    void setEstrategiaRepeticion(IEstrategiaRepeticion estrategiaRepeticion);
    IEstrategiaRepeticion getEstrategiaRepeticion();
    void setDeckRepository(IDeckRepository deckRepository);
    IDeckRepository getDeckRepository();
    void practiceDeck(DeckDTO deck, IEstrategiaRepeticion estrategia, IUserPracticeInputPort userInputPort);
    List<FlashcardDTO> getFlashcardsByDeckId(UUID deckId);
    List<FlashcardDTO> getFlashcardsToPractice(DeckDTO deck);
}
