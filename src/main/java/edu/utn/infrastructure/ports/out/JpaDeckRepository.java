package edu.utn.infrastructure.ports.out;

import edu.utn.infrastructure.adapters.out.persistence.entities.DeckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaDeckRepository extends JpaRepository<DeckEntity, UUID> {
    void deleteById(UUID id);

    @Query("SELECT d FROM DeckEntity d LEFT JOIN FETCH d.flashcards WHERE d.id = :id")
    Optional<DeckEntity> findByIdWithFlashcards(@Param("id") UUID id);
}