package edu.utn.infrastructure.adapters.in.rest.controller;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.usecase.flashcard.*;
import edu.utn.infrastructure.ports.in.IFlashcardController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/flashcards")
@CrossOrigin(origins = "http://localhost:63342")
public class FlashcardController implements IFlashcardController {

    private final GetFlashcardUseCase getFlashcardUseCase;
    private final CreateFlashcardUseCase createFlashcardUseCase;
    private final ModifyFlashcardUseCase modifyFlashcardUseCase;
    private final DeleteFlashcardUseCase deleteFlashcardUseCase;
    private final AddFlashcardToDeckUseCase addFlashcardToDeckUseCase;

    public FlashcardController(
            GetFlashcardUseCase getFlashcardUseCase,
            CreateFlashcardUseCase createFlashcardUseCase,
            ModifyFlashcardUseCase modifyFlashcardUseCase,
            DeleteFlashcardUseCase deleteFlashcardUseCase,
            AddFlashcardToDeckUseCase addFlashcardToDeckUseCase) {
        this.getFlashcardUseCase = getFlashcardUseCase;
        this.createFlashcardUseCase = createFlashcardUseCase;
        this.modifyFlashcardUseCase = modifyFlashcardUseCase;
        this.deleteFlashcardUseCase = deleteFlashcardUseCase;
        this.addFlashcardToDeckUseCase = addFlashcardToDeckUseCase;
    }

    // 1) Obtener flashcard por ID
    @GetMapping("/{flashcardId}")
    public ResponseEntity<FlashcardDTO> getFlashcardById(@PathVariable UUID flashcardId) {
        FlashcardDTO dto = getFlashcardUseCase.execute(flashcardId);
        return dto != null
                ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
    }

    // 2) (Opcional) Crear flashcard vinculada a deck
    //    Si prefieres manejar creación solo desde DeckController, podés omitir este método.
    @PostMapping("/deck/{deckId}")
    public ResponseEntity<FlashcardDTO> createFlashcard(
            @PathVariable UUID deckId,
            @RequestBody FlashcardDTO flashcardDto
    ) {
        FlashcardDTO created = createFlashcardUseCase.execute(flashcardDto.getPregunta(), flashcardDto.getRespuesta());
        addFlashcardToDeckUseCase.execute(deckId, created);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // 3) Modificar flashcard
    @PutMapping("/{flashcardId}")
    public ResponseEntity<FlashcardDTO> modifyFlashcard(
            @PathVariable UUID flashcardId,
            @RequestBody FlashcardDTO flashcardDto
    ) {
        flashcardDto.setId(flashcardId);
        FlashcardDTO updated = modifyFlashcardUseCase.execute(flashcardDto);
        return ResponseEntity.ok(updated);
    }

    // 4) Borrar flashcard
    @DeleteMapping("/{flashcardId}")
    public ResponseEntity<Void> deleteFlashcard(@PathVariable UUID flashcardId) {
        deleteFlashcardUseCase.execute(flashcardId);
        return ResponseEntity.noContent().build();
    }
}
