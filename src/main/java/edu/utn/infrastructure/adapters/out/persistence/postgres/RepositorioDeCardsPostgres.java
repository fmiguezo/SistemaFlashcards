package edu.utn.infrastructure.adapters.out.persistence.postgres;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import edu.utn.infrastructure.ports.out.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RepositorioDeCardsPostgres implements IFlashcardRepository {
    private final JpaRepository jpaRepository;

    public RepositorioDeCardsPostgres(JpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public IFlashcard createCard(IFlashcard card) {
        return jpaRepository.saveFlashcard(card);
    }

    @Override
    public Optional<IFlashcard> getCardById(UUID id) {
        return jpaRepository.findCardById(id);
    }

    @Override
    public void updateCard(IFlashcard card) {
        jpaRepository.updateFlashcard(card);
    }

    @Override
    public void deleteCard(UUID id) {
        jpaRepository.deleteCardById(id);
    }
}
