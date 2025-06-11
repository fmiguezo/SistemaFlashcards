package edu.utn.infrastructure.adapters.in.rest.controller;

import java.util.List;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.usecase.deck.*;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.estrategia.EstrategiaRepeticionEstandar;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.infrastructure.ports.in.IDeckController;

import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
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
    private final GetDeckUseCase getDeckUseCase;
    private final GetAllDecksUseCase getAllDecksUseCase;

    public DeckController(CreateDeckUseCase createDeckUseCase, DeleteDeckUseCase deleteDeckUseCase, ModifyDeckUseCase modifyDeckUseCase, PracticeDeckUseCase practiceDeckUseCase, ListFlashcardsUseCase listFlashcardsUseCase, AddFlashcardToDeckUseCase addFlashcardToDeckUseCase, GetDeckUseCase getDeckUseCase, GetAllDecksUseCase getAllDecksUseCase) {
        this.createDeckUseCase = createDeckUseCase;
        this.deleteDeckUseCase = deleteDeckUseCase;
        this.modifyDeckUseCase = modifyDeckUseCase;
        this.practiceDeckUseCase = practiceDeckUseCase;
        this.listFlashcardsUseCase = listFlashcardsUseCase;
        this.addFlashcardToDeckUseCase = addFlashcardToDeckUseCase;
        this.getDeckUseCase = getDeckUseCase;
        this.getAllDecksUseCase = getAllDecksUseCase;
    }

    @GetMapping
    public ResponseEntity<List<DeckDTO>> getAllDecks() {
        List<DeckDTO> decks = getAllDecksUseCase.execute();
        return ResponseEntity.ok(decks);
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

    @GetMapping("/{deckId}")
    public ResponseEntity<DeckDTO> getDeckById(@PathVariable UUID deckId) {
        DeckDTO deck = getDeckUseCase.execute(deckId); // Usar el caso de uso
        if (deck == null) {
            return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra el deck
        }
        return ResponseEntity.ok(deck); // Retorna el DTO del deck
    }

    @GetMapping("/{deckId}/practice")
    @Override
    public ResponseEntity<?> practiceDeck(@PathVariable UUID deckId) {
        DeckDTO deck = getDeckUseCase.execute(deckId);
        IEstrategiaRepeticion estrategia = new EstrategiaRepeticionEstandar();
        IUserPracticeInputPort userInputPort = new WebUserPracticeController();
        practiceDeckUseCase.execute(deck,estrategia,userInputPort);
        return ResponseEntity.ok("Se inicio la practica del deck: correctamente");
    }

    @GetMapping("/{deckId}/flashcards")
    @Override
    public ResponseEntity<List<FlashcardDTO>> listFlashcards(@PathVariable UUID deckId) {
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
