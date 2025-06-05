package edu.utn.infrastructure.adapters.out.persistence;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.domain.model.Flashcard;
import edu.utn.domain.model.IFlashcard;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import edu.utn.infrastructure.ports.out.JpaDeckRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public class RepositorioDeCardsPostgres implements IFlashcardRepository {
    private final JpaDeckRepository jpaDeckRepository;

    public RepositorioDeCardsPostgres(JpaDeckRepository jpaDeckRepository) {
        this.jpaDeckRepository = jpaDeckRepository;
    }

    @Override
    public IFlashcard createCard(IFlashcard card) {
        return null;
    }

    @Override
    public IFlashcard getCardById(UUID id) {
        return null;
    }

    @Override
    public void updateCard(IFlashcard card) {

    }

    @Override
    public void deleteCard(UUID id) {

    }
}
