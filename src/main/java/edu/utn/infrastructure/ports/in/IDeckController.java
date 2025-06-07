package edu.utn.infrastructure.ports.in;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;


public interface IDeckController {
    // Lo uso para crear un deck
    ResponseEntity<?> createDeck(@RequestBody DeckDTO deckDto);

    // Lo uso para modificar un deck
    ResponseEntity<?> modifyDeck(@RequestBody DeckDTO deckDto);

    // Lo uso para eliminar un deck
    ResponseEntity<?> deleteDeck(@PathVariable UUID deckId);

    // Lo uso para utilizar el practiceDeck
    ResponseEntity<?> practiceDeck(@PathVariable UUID deckId);

    // Listar flashcards de un mazo
    ResponseEntity<?> listFlashcards(@PathVariable UUID deckId);

    // Agregar una flashcard a un deck
    ResponseEntity<?> addFlashcardToDeck(@PathVariable UUID deckId, @RequestBody FlashcardDTO flashcardDto);
}
