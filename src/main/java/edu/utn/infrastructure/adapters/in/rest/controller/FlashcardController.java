package edu.utn.infrastructure.adapters.in.rest.controller;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.usecase.flashcard.CreateFlashcardUseCase;
import edu.utn.application.usecase.flashcard.ModifyFlashcardUseCase;
import edu.utn.application.usecase.flashcard.DeleteFlashcardUseCase;
import edu.utn.application.usecase.flashcard.GetFlashcardUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    private final GetFlashcardUseCase getFlashcardUseCase;
    private final CreateFlashcardUseCase createFlashcardUseCase;
    private final ModifyFlashcardUseCase modifyFlashcardUseCase;
    private final DeleteFlashcardUseCase deleteFlashcardUseCase;

    public FlashcardController(
            GetFlashcardUseCase getFlashcardUseCase,
            CreateFlashcardUseCase createFlashcardUseCase,
            ModifyFlashcardUseCase modifyFlashcardUseCase,
            DeleteFlashcardUseCase deleteFlashcardUseCase
    ) {
        this.getFlashcardUseCase = getFlashcardUseCase;
        this.createFlashcardUseCase = createFlashcardUseCase;
        this.modifyFlashcardUseCase = modifyFlashcardUseCase;
        this.deleteFlashcardUseCase = deleteFlashcardUseCase;
    }

    // 1) Obtener flashcard por ID
    @GetMapping("/{flashcardId}")
    public ResponseEntity<FlashcardDTO> getFlashcardById(@PathVariable UUID flashcardId) {
        FlashcardDTO dto = getFlashcardUseCase.execute(flashcardId);
        return dto != null
                ? ResponseEntity.ok(dto)
                : ResponseEntity.notFound().build();
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
