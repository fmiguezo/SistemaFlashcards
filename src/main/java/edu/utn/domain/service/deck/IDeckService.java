package edu.utn.domain.service.deck;
import edu.utn.application.dto.DeckDTO;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import edu.utn.infrastructure.ports.out.IDeckRepository;

import java.util.List;
import java.util.UUID;

public interface IDeckService {
    void addDeck(DeckDTO deck);
    DeckDTO getDeckById(UUID id);
    void updateDeck(DeckDTO deck);
    void deleteDeckById(UUID id);
    List<IDeck> getAllDecks();
    void setEstrategiaRepeticion(IEstrategiaRepeticion estrategiaRepeticion);
    IEstrategiaRepeticion getEstrategiaRepeticion();
    void setDeckRepository(IDeckRepository deckRepository);
    IDeckRepository getDeckRepository();
    void practiceDeck(DeckDTO deck, IEstrategiaRepeticion estrategia, IUserPracticeInputPort userInputPort);
    List<IFlashcard> getFlashcardsByDeckId(UUID deckId);
    List<IFlashcard> getFlashcardsToPractice(DeckDTO deck);
}
