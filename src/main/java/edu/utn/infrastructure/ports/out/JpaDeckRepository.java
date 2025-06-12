package edu.utn.infrastructure.ports.out;

import edu.utn.infrastructure.adapters.out.persistence.entities.DeckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaDeckRepository extends JpaRepository<DeckEntity, UUID> {
    void deleteById(UUID id);
}