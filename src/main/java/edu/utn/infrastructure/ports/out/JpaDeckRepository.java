package edu.utn.infrastructure.ports.out;
import edu.utn.domain.model.Deck;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface JpaDeckRepository extends JpaRepository<Deck, Long> {
    // Métodos personalizados si necesitas
}

