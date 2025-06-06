package edu.utn.infrastructure.adapters.in.rest.controller;
import edu.utn.infrastructure.ports.in.IDeckController;

public class DeckController implements IDeckController {
    // This class will handle HTTP requests related to Decks
    // It will interact with the DeckService to perform operations like creating, updating, deleting, and retrieving decks

    // Example method to create a new deck
    // @PostMapping("/decks")
    // public ResponseEntity<Deck> createDeck(@RequestBody Deck deck) {
    //     Deck createdDeck = deckService.createDeck(deck);
    //     return new ResponseEntity<>(createdDeck, HttpStatus.CREATED);
    // }
}
