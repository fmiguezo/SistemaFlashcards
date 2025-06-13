package edu.utn.infrastructure.adapters.in.rest.controller;

import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.usecase.deck.*;
import edu.utn.application.usecase.flashcard.AddFlashcardToDeckUseCase;
import edu.utn.application.usecase.flashcard.ListFlashcardsUseCase;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.estrategia.EstrategiaRepeticionEstandar;
import edu.utn.infrastructure.ports.in.IDeckController;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/decks")
@CrossOrigin(origins = "http://localhost:63342")
public class DeckController implements IDeckController{

    private final CreateDeckUseCase createDeckUseCase;
    private final ListDecksUseCase listDecksUseCase;
    private final GetDeckUseCase getDeckUseCase;
    private final ModifyDeckUseCase modifyDeckUseCase;
    private final DeleteDeckUseCase deleteDeckUseCase;
    private final ListFlashcardsUseCase listFlashcardsUseCase;
    private final AddFlashcardToDeckUseCase addFlashcardToDeckUseCase;
    private final PracticeDeckUseCase practiceDeckUseCase;

    public DeckController(
            CreateDeckUseCase createDeckUseCase,
            ListDecksUseCase listDecksUseCase,
            GetDeckUseCase getDeckUseCase,
            ModifyDeckUseCase modifyDeckUseCase,
            DeleteDeckUseCase deleteDeckUseCase,
            ListFlashcardsUseCase listFlashcardsUseCase,
            AddFlashcardToDeckUseCase addFlashcardToDeckUseCase,
            PracticeDeckUseCase practiceDeckUseCase
    ) {
        this.createDeckUseCase = createDeckUseCase;
        this.listDecksUseCase = listDecksUseCase;
        this.getDeckUseCase = getDeckUseCase;
        this.modifyDeckUseCase = modifyDeckUseCase;
        this.deleteDeckUseCase = deleteDeckUseCase;
        this.listFlashcardsUseCase = listFlashcardsUseCase;
        this.addFlashcardToDeckUseCase = addFlashcardToDeckUseCase;
        this.practiceDeckUseCase = practiceDeckUseCase;
    }

    // 1) Listar todos los decks
    @GetMapping
    public ResponseEntity<List<DeckDTO>> listDecks() {
        List<DeckDTO> decks = listDecksUseCase.execute();
        return ResponseEntity.ok(decks);
    }

    // 2) Obtener un deck por ID
    @GetMapping("/{deckId}")
    public ResponseEntity<DeckDTO> getDeckById(@PathVariable UUID deckId) {
        DeckDTO deck = getDeckUseCase.execute(deckId);
        return deck != null
                ? ResponseEntity.ok(deck)
                : ResponseEntity.notFound().build();
    }

    // 3) Crear un deck
    @PostMapping
    public ResponseEntity<DeckDTO> createDeck(@RequestBody DeckDTO deckDto) {
        DeckDTO created = createDeckUseCase.execute(deckDto.getNombre(), deckDto.getDescripcion());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // 4) Modificar un deck
    @PutMapping("/{deckId}")
    public ResponseEntity<DeckDTO> modifyDeck(
            @PathVariable UUID deckId,
            @RequestBody DeckDTO deckDto
    ) {
        deckDto.setId(deckId);
        DeckDTO modified = modifyDeckUseCase.execute(deckDto);
        return ResponseEntity.ok(modified);
    }

    // 5) Borrar un deck
    @DeleteMapping("/{deckId}")
    public ResponseEntity<Void> deleteDeck(@PathVariable UUID deckId) {
        deleteDeckUseCase.execute(deckId);
        return ResponseEntity.noContent().build();
    }

    // 6) Listar flashcards de un deck
    @GetMapping("/{deckId}/flashcards")
    public ResponseEntity<List<FlashcardDTO>> listFlashcards(@PathVariable UUID deckId) {
        List<FlashcardDTO> flashcards = listFlashcardsUseCase.execute(deckId);
        return ResponseEntity.ok(flashcards);
    }

    // 7) Agregar una flashcard a un deck
    @PostMapping("/{deckId}/flashcards")
    public ResponseEntity<FlashcardDTO> addFlashcardToDeck(
            @PathVariable UUID deckId,
            @RequestBody FlashcardDTO flashcardDto
    ) {
        FlashcardDTO created = addFlashcardToDeckUseCase.execute(deckId, flashcardDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // 8) Iniciar práctica de un deck
    @GetMapping("/{deckId}/practice")
    public ResponseEntity<Void> practiceDeck(@PathVariable UUID deckId) {
        DeckDTO deck = getDeckUseCase.execute(deckId);
        practiceDeckUseCase.execute(
                deck,
                new EstrategiaRepeticionEstandar(),
                new WebUserPracticeController() // tu implementación de IUserPracticeInputPort
        );
        return ResponseEntity.ok().build();
    }
}
