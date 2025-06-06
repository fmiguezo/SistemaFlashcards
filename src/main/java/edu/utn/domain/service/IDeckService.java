package edu.utn.domain.service;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.model.IEstrategiaRepeticion;
import edu.utn.domain.model.IFlashcard;
import edu.utn.infrastructure.ports.out.IDeckRepository;

import java.util.List;
import java.util.UUID;

public interface IDeckService {
    void addDeck(IDeck deck);
    IDeck getDeckById(UUID id);
    void updateDeck(IDeck deck);
    void deleteDeck(UUID id);
    List<IDeck> getAllDecks();
    void setEstrategiaRepeticion(IEstrategiaRepeticion estrategiaRepeticion);
    IEstrategiaRepeticion getEstrategiaRepeticion();
    void setDeckRepository(IDeckRepository deckRepository);
    IDeckRepository getDeckRepository();
    List<IFlashcard> getFlashcardsToPractice(IDeck deck);
    void practiceDeck(IDeck deck, IEstrategiaRepeticion estrategia);
}
