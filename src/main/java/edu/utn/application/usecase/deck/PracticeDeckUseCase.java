package edu.utn.application.usecase.deck;

import edu.utn.application.dto.DeckDTO;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.service.deck.IDeckService;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;

public class PracticeDeckUseCase {
    private final IDeckService deckService;

    public PracticeDeckUseCase(IDeckService deckService) {
        this.deckService = deckService;
    }

    public void execute(DeckDTO deck, IEstrategiaRepeticion estrategia, IUserPracticeInputPort userInputPort) {
        if (deck == null) throw new IllegalArgumentException("deck no puede ser null");
        if (estrategia == null) throw new IllegalArgumentException("estrategia no puede ser null");
        if (userInputPort == null) throw new IllegalArgumentException("userInputPort no puede ser null");

        deckService.practiceDeck(deck, estrategia, userInputPort);
    }
}
