package edu.utn.infrastructure.adapters.in.rest.controller;
import edu.utn.application.dto.DeckDTO;
import edu.utn.application.usecase.CreateDeckUseCase;
import edu.utn.application.usecase.DeleteDeckUseCase;
import edu.utn.application.usecase.ModifyDeckUseCase;
import edu.utn.application.usecase.PracticeDeckUseCase;
import edu.utn.infrastructure.ports.in.IDeckController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/decks")
public class DeckController implements IDeckController {
    private final CreateDeckUseCase createDeckUseCase;
    private final DeleteDeckUseCase deleteDeckUseCase;
    private final ModifyDeckUseCase modifyDeckUseCase;
    private final PracticeDeckUseCase practiceDeckUseCase;

    public DeckController(CreateDeckUseCase createDeckUseCase, DeleteDeckUseCase deleteDeckUseCase, ModifyDeckUseCase modifyDeckUseCase, PracticeDeckUseCase practiceDeckUseCase) {
        this.createDeckUseCase = createDeckUseCase;
        this.deleteDeckUseCase = deleteDeckUseCase;
        this.modifyDeckUseCase = modifyDeckUseCase;
        this.practiceDeckUseCase = practiceDeckUseCase;
    }

    @PostMapping
    @Override
    public ResponseEntity<?> createDeck(@RequestBody DeckDTO deckDto) {
        DeckDTO result = createDeckUseCase.createDeck(deckDto);
        return ResponseEntity.ok("Se creo el deck: " + result.getNombre() + " correctamente");
    }

    @PutMapping("/{deckId}")
    @Override
    public ResponseEntity<?> modifyDeck(@PathVariable UUID deckId, @RequestBody DeckDTO deckDto) {
        DeckDTO result = modifyDeckUseCase.modifyDeck(deckId, deckDto);
        return ResponseEntity.ok("Se modifico el deck: " + result.getNombre() + " correctamente");
    }

    @DeleteMapping("/{deckId}")
    @Override
    public ResponseEntity<?> deleteDeck(@PathVariable UUID deckId) {
        DeckDTO result = deleteDeckUseCase.deleteDeck(deckId);
        return ResponseEntity.ok("Se elimino el deck: " + result.getNombre() + " correctamente");
    }

    @GetMapping("/{deckId}/practice")
    @Override
    public ResponseEntity<?> practiceDeck(@PathVariable UUID deckId) {
        DeckDTO result = practiceDeckUseCase.practiceDeck(deckId);
        return ResponseEntity.ok("Se inicio la practica del deck: " + result.getNombre() + " correctamente");
    }
}
