package edu.utn.infrastructure.ports.out;
import edu.utn.domain.model.Flashcard;
import java.util.List;

public interface IFlashcardRepository {
    List<Flashcard> getCards(String deckId);
    void updateCard(Flashcard card);
    void deleteCard(Flashcard card);
}
