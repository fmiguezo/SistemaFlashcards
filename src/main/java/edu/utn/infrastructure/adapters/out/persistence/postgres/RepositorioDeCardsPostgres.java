package edu.utn.infrastructure.adapters.out.persistence.postgres;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import edu.utn.infrastructure.ports.out.JpaDeckRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RepositorioDeCardsPostgres implements IFlashcardRepository {
    private final JpaDeckRepository jpaDeckRepository;

    public RepositorioDeCardsPostgres(JpaDeckRepository jpaDeckRepository) {
        this.jpaDeckRepository = jpaDeckRepository;
    }

    @Override
    public IFlashcard createCard(IFlashcard card) {
        return jpaDeckRepository.saveFlashcard(card);
    }

    @Override
    public Optional<IFlashcard> getCardById(UUID id) {
        return jpaDeckRepository.findCardById(id);
    }

    @Override
    public void updateCard(IFlashcard card) {
        jpaDeckRepository.updateFlashcard(card);
    }

    @Override
    public void deleteCard(UUID id) {
        jpaDeckRepository.deleteCardById(id);
    }
}
