package edu.utn.domain.service;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.model.IEstrategiaRepeticion;
import edu.utn.infrastructure.ports.out.IDeckRepository;

import java.util.List;
import java.util.UUID;

public class DeckService implements IDeckService{
    private IDeckRepository deckRepository;
    private IEstrategiaRepeticion estrategiaRepeticion;

    public DeckService(IDeckRepository deckRepository,
                       IEstrategiaRepeticion estrategiaRepeticion) {
        this.deckRepository = deckRepository;
        this.estrategiaRepeticion = estrategiaRepeticion;
    }

    public IDeckRepository getDeckRepository() {
        return deckRepository;
    }

    public void setDeckRepository(IDeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    public IEstrategiaRepeticion getEstrategiaRepeticion() {
        return estrategiaRepeticion;
    }

    public void setEstrategiaRepeticion(IEstrategiaRepeticion estrategiaRepeticion) {
        this.estrategiaRepeticion = estrategiaRepeticion;
    }

    public void addDeck(IDeck deck) {
        deckRepository.createDeck(deck);
    }

    public IDeck getDeckById(UUID id) {
        return deckRepository.getDeckById(id);
    }

    public void updateDeck(IDeck deck) {
        deckRepository.updateDeck(deck);
    }

    public void deleteDeck(UUID id) {
        deckRepository.deleteDeckById(id);
    }

    public List<IDeck> getAllDecks() {
        return deckRepository.getAllDecks();
    }

    public void practiceDeck(IDeck deck) {

    }
}
