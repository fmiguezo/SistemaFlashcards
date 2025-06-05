package edu.utn.infrastructure.adapters.out.persistence;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.domain.model.Flashcard;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import edu.utn.infrastructure.ports.out.JpaDeckRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RepositorioDeCardsPostgres implements IFlashcardRepository {
    private final JpaDeckRepository jpaDeckRepository;
    private final DeckMapper mapper;

    public RepositorioDeCardsPostgres(JpaDeckRepository jpaDeckRepository, DeckMapper mapper) {
        this.jpaDeckRepository = jpaDeckRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Flashcard> getCards(String deckId) {
        return List.of();
    }

    @Override
    public void updateCard(Flashcard card) {

    }

    @Override
    public void deleteCard(Flashcard card) {

    }
}
