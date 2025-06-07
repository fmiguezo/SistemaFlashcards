package edu.utn.infrastructure.ports.in;

import edu.utn.application.dto.DeckDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface IDeckController {
    // Lo uso para crear un deck
    ResponseEntity<?> createDeck(@RequestBody DeckDTO deckDto);

    // Lo uso para modificar un deck
    ResponseEntity<?> modifyDeck(@PathVariable UUID deckId, @RequestBody DeckDTO deckDto);

    // Lo uso para eliminar un deck
    ResponseEntity<?> deleteDeck(@PathVariable UUID deckId);

    // Lo uso para utilizar el practiceDeck
    ResponseEntity<?> practiceDeck(@PathVariable UUID deckId);
}
