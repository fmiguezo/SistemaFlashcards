package edu.utn.infrastructure.adapters.in.rest.controller;
import java.util.List;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.usecase.deck.CreateDeckUseCase;
import edu.utn.application.usecase.deck.DeleteDeckUseCase;
import edu.utn.application.usecase.deck.ModifyDeckUseCase;
import edu.utn.application.usecase.deck.PracticeDeckUseCase;
import edu.utn.infrastructure.ports.in.IDeckController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import edu.utn.application.usecase.flashcard.AddFlashcardToDeckUseCase;
import edu.utn.application.usecase.flashcard.ListFlashcardsUseCase;

@RestController
@RequestMapping("/api/decks")
public class DeckController implements IDeckController {
    private final CreateDeckUseCase createDeckUseCase;
    private final DeleteDeckUseCase deleteDeckUseCase;
    private final ModifyDeckUseCase modifyDeckUseCase;
    private final PracticeDeckUseCase practiceDeckUseCase;
    private final ListFlashcardsUseCase listFlashcardsUseCase;
    private final AddFlashcardToDeckUseCase addFlashcardToDeckUseCase;

    public DeckController(CreateDeckUseCase createDeckUseCase, DeleteDeckUseCase deleteDeckUseCase, ModifyDeckUseCase modifyDeckUseCase, PracticeDeckUseCase practiceDeckUseCase, ListFlashcardsUseCase listFlashcardsUseCase, AddFlashcardToDeckUseCase addFlashcardToDeckUseCase) {
        this.createDeckUseCase = createDeckUseCase;
        this.deleteDeckUseCase = deleteDeckUseCase;
        this.modifyDeckUseCase = modifyDeckUseCase;
        this.practiceDeckUseCase = practiceDeckUseCase;
        this.listFlashcardsUseCase = listFlashcardsUseCase;
        this.addFlashcardToDeckUseCase = addFlashcardToDeckUseCase;
    }

    @PostMapping
    @Override
    public ResponseEntity<?> createDeck(@RequestBody DeckDTO deckDto) {
        DeckDTO result = createDeckUseCase.execute(deckDto);
        return ResponseEntity.ok("Se creo el deck: " + result.getNombre() + " correctamente");
    }

    @PutMapping("/{deckId}")
    @Override
    public ResponseEntity<?> modifyDeck(@RequestBody DeckDTO deckDto) {
        DeckDTO result = modifyDeckUseCase.execute(deckDto);
        return ResponseEntity.ok("Se modifico el deck: " + result.getNombre() + " correctamente");
    }

    @DeleteMapping("/{deckId}")
    @Override
    public ResponseEntity<?> deleteDeck(@PathVariable UUID deckId) {
        String result = deleteDeckUseCase.execute(deckId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{deckId}/practice")
    @Override
    public ResponseEntity<?> practiceDeck(@PathVariable UUID deckId) {

        // esto hay que actualizarlo porque esta mal, no debe crear un DTO, y los parametros estan incorrectos
        DeckDTO result = practiceDeckUseCase.practiceDeck(deckId);
        return ResponseEntity.ok("Se inicio la practica del deck: " + result.getNombre() + " correctamente");
    }

    @GetMapping("/deck/{deckId}")
    @Override
    public ResponseEntity<?> listFlashcards(@PathVariable UUID deckId) {
        List<FlashcardDTO> result = listFlashcardsUseCase.execute(deckId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{deckId}/flashcards")
    @Override
    public ResponseEntity<?> addFlashcardToDeck(@PathVariable UUID deckId, @RequestBody FlashcardDTO flashcardDto) {
        FlashcardDTO result = addFlashcardToDeckUseCase.execute(deckId, flashcardDto);
        return ResponseEntity.ok("Se agrego la flashcard: " + result.getPregunta() + " al deck: " + deckId + " correctamente");
    }

}
