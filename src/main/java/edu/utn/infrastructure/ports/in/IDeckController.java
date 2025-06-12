package edu.utn.infrastructure.ports.in;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;


public interface IDeckController {

    // 1) Listar todos los decks
    ResponseEntity<List<DeckDTO>> listDecks();

    // 2) Obtener un deck por ID
    ResponseEntity<DeckDTO> getDeckById(@PathVariable UUID deckId);

    // 3) Crear un deck
    ResponseEntity<DeckDTO> createDeck(@RequestBody DeckDTO deckDto);

    // 4) Modificar un deck
    ResponseEntity<DeckDTO> modifyDeck(
            @PathVariable UUID deckId,
            @RequestBody DeckDTO deckDto
    );

    // 5) Borrar un deck
    ResponseEntity<Void> deleteDeck(@PathVariable UUID deckId);

    // 6) Listar flashcards de un deck
    ResponseEntity<List<FlashcardDTO>> listFlashcards(@PathVariable UUID deckId);

    // 7) Agregar una flashcard a un deck
    ResponseEntity<FlashcardDTO> addFlashcardToDeck(
            @PathVariable UUID deckId,
            @RequestBody FlashcardDTO flashcardDto
    );

    // 8) Iniciar práctica de un deck
    ResponseEntity<Void> practiceDeck(@PathVariable UUID deckId);
}
