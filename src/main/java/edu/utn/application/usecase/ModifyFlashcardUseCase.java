package edu.utn.application.usecase;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.application.error.FlashcardError;
import edu.utn.domain.model.IFlashcard;
import edu.utn.domain.service.IFlashcardService;

public class ModifyFlashcardUseCase {
    private final IFlashcardService flashcardService;
    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 500;

    public ModifyFlashcardUseCase(IFlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    public FlashcardDTO execute(FlashcardDTO flashcardDTO) {
        IFlashcard flashcard = validateInput(flashcardDTO);
        
        if (flashcardDTO.getPregunta() != null) {
            flashcard.setPregunta(flashcardDTO.getPregunta());
        }
        
        if (flashcardDTO.getRespuesta() != null) {
            flashcard.setRespuesta(flashcardDTO.getRespuesta());
        }
        
        flashcardService.updateFlashcard(flashcard);
        return FlashcardMapper.toDTO(flashcard);
    }

    private IFlashcard validateInput(FlashcardDTO flashcardDTO) {
        if (flashcardDTO == null) {
            throw FlashcardError.nullFlashcard();
        }

        if (flashcardDTO.getId() == null) {
            throw FlashcardError.nullFlashcardId();
        }

        if (flashcardDTO.getPregunta() == null && flashcardDTO.getRespuesta() == null) {
            throw new IllegalArgumentException("Debe proporcionar al menos una pregunta o respuesta para modificar");
        }

        IFlashcard existingFlashcard = flashcardService.getFlashcardById(flashcardDTO.getId());
        if (existingFlashcard == null) {
            throw FlashcardError.flashcardNotFound();
        }

        if (flashcardDTO.getPregunta() != null) {
            if (flashcardDTO.getPregunta().trim().isEmpty()) {
                throw FlashcardError.emptyQuestion();
            }
            if (flashcardDTO.getPregunta().length() < MIN_LENGTH) {
                throw FlashcardError.questionTooShort();
            }
            if (flashcardDTO.getPregunta().length() > MAX_LENGTH) {
                throw FlashcardError.questionTooLong();
            }
            if (flashcardDTO.getPregunta().equals(existingFlashcard.getPregunta())) {
                throw FlashcardError.sameQuestion();
            }
        }

        if (flashcardDTO.getRespuesta() != null) {
            if (flashcardDTO.getRespuesta().trim().isEmpty()) {
                throw FlashcardError.emptyAnswer();
            }
            if (flashcardDTO.getRespuesta().length() < MIN_LENGTH) {
                throw FlashcardError.answerTooShort();
            }
            if (flashcardDTO.getRespuesta().length() > MAX_LENGTH) {
                throw FlashcardError.answerTooLong();
            }
            if (flashcardDTO.getRespuesta().equals(existingFlashcard.getRespuesta())) {
                throw FlashcardError.sameAnswer();
            }
        }

        return existingFlashcard;
    }
}
