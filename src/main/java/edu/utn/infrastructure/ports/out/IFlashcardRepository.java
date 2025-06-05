package edu.utn.infrastructure.ports.out;
import edu.utn.domain.model.Flashcard;
import edu.utn.domain.model.IFlashcard;

import java.util.UUID;

public interface IFlashcardRepository {
    IFlashcard createCard(IFlashcard card);
    IFlashcard getCardById(UUID id);
    void updateCard(IFlashcard card);
    void deleteCard(UUID id);
}
