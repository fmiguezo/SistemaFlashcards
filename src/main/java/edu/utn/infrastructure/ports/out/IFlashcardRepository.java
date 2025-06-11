package edu.utn.infrastructure.ports.out;
import edu.utn.domain.model.flashcard.IFlashcard;

import java.util.Optional;
import java.util.UUID;

public interface IFlashcardRepository {
    IFlashcard createCard(IFlashcard card);
    Optional<IFlashcard> getCardById(UUID id);
    void updateCard(IFlashcard card);
    void deleteCard(UUID id);
}
