package edu.utn.infrastructure.adapters.in.rest.controller;

import java.util.UUID;

import edu.utn.application.usecase.flashcard.GetFlashcardUseCase;
import edu.utn.application.usecase.flashcard.CreateFlashcardUseCase;
import edu.utn.application.usecase.flashcard.ModifyFlashcardUseCase;
import edu.utn.application.usecase.flashcard.DeleteFlashcardUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.utn.application.dto.FlashcardDTO;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    private final CreateFlashcardUseCase createFlashcardUseCase;
    private final ModifyFlashcardUseCase modifyFlashcardUseCase;
    private final DeleteFlashcardUseCase deleteFlashcardUseCase;
    private final GetFlashcardUseCase getFlashcardUseCase;

    public FlashcardController(CreateFlashcardUseCase createFlashcardUseCase, ModifyFlashcardUseCase modifyFlashcardUseCase, DeleteFlashcardUseCase deleteFlashcardUseCase, GetFlashcardUseCase getFlashcardUseCase) {
        this.createFlashcardUseCase = createFlashcardUseCase;
        this.modifyFlashcardUseCase = modifyFlashcardUseCase;
        this.deleteFlashcardUseCase = deleteFlashcardUseCase;
        this.getFlashcardUseCase = getFlashcardUseCase;
    }

    @GetMapping("/{flashcardId}")
    public ResponseEntity<FlashcardDTO> getFlashcardById(@PathVariable UUID flashcardId) {
        FlashcardDTO flashcard = getFlashcardUseCase.execute(flashcardId); // Usar el caso de uso
        if (flashcard == null) {
            return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra la flashcard
        }
        return ResponseEntity.ok(flashcard); // Retorna el DTO de la flashcard
    }

    @PostMapping("/deck/{deckId}")
    public ResponseEntity<?> createFlashcard(@PathVariable UUID deckId, @RequestBody FlashcardDTO flashcardDto) {
        FlashcardDTO result = createFlashcardUseCase.execute(flashcardDto);
        return ResponseEntity.ok("Se creo la flashcard: " + result.getPregunta() + " correctamente");
    }

    @PutMapping("/{flashcardId}")
    public ResponseEntity<?> modifyFlashcard(@PathVariable UUID flashcardId, @RequestBody FlashcardDTO flashcardDto) {
        FlashcardDTO result = modifyFlashcardUseCase.execute(flashcardDto);
        return ResponseEntity.ok("Se modifico la flashcard: " + result.getPregunta() + " correctamente");
    }

    @DeleteMapping("/{flashcardId}")
    public ResponseEntity<?> deleteFlashcard(@PathVariable UUID flashcardId) {
        String result = deleteFlashcardUseCase.execute(flashcardId);
        return ResponseEntity.ok(result);
    }
}
