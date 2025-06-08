package infrastructure.adapters.out.persistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.utn.domain.model.Flashcard;
import edu.utn.domain.model.IFlashcard;
import edu.utn.infrastructure.adapters.out.persistence.RepositorioDeCardsJson;

class RepositorioDeCardsJsonTest {

    @TempDir
    Path tempDir;
    private File testFile;
    private RepositorioDeCardsJson repositorio;

    @BeforeEach
    void setUp() throws IOException {
        testFile = tempDir.resolve("test-cards.json").toFile();
        repositorio = new RepositorioDeCardsJson(testFile.getAbsolutePath());
    }

    @Test
    void createCard_ShouldPersistCardToFile() throws IOException {
        // Arrange
        IFlashcard card = new Flashcard("Test Question", "Test Answer");
        
        // Act
        IFlashcard savedCard = repositorio.createCard(card);
        
        // Assert
        assertNotNull(savedCard);
        assertEquals("Test Question", savedCard.getPregunta());
        assertEquals("Test Answer", savedCard.getRespuesta());
        assertTrue(testFile.exists());
        assertTrue(testFile.length() > 0);
        
        // Verify JSON content
        String jsonContent = Files.readString(testFile.toPath());
        assertTrue(jsonContent.contains("Test Question"));
        assertTrue(jsonContent.contains("Test Answer"));
    }

    @Test
    void getCardById_ShouldReturnCorrectCard() {
        // Arrange
        IFlashcard card = new Flashcard("Test Question", "Test Answer");
        IFlashcard savedCard = repositorio.createCard(card);
        UUID cardId = savedCard.getId();

        // Act
        IFlashcard retrievedCard = repositorio.getCardById(cardId);

        // Assert
        assertNotNull(retrievedCard);
        assertEquals(cardId, retrievedCard.getId());
        assertEquals("Test Question", retrievedCard.getPregunta());
        assertEquals("Test Answer", retrievedCard.getRespuesta());
        assertNotNull(retrievedCard.getCreatedAt());
        assertNotNull(retrievedCard.getUpdatedAt());
    }

    @Test
    void getCardById_WithNonExistentId_ShouldReturnNull() {
        // Act
        IFlashcard card = repositorio.getCardById(UUID.randomUUID());

        // Assert
        assertNull(card);
    }

    @Test
    void updateCard_ShouldUpdateExistingCard() {
        // Arrange
        IFlashcard card = new Flashcard("Original Question", "Original Answer");
        IFlashcard savedCard = repositorio.createCard(card);
        UUID cardId = savedCard.getId();
        LocalDateTime originalUpdatedAt = savedCard.getUpdatedAt();

        // Act
        savedCard.setPregunta("Updated Question");
        savedCard.setRespuesta("Updated Answer");
        repositorio.updateCard(savedCard);

        // Assert
        IFlashcard updatedCard = repositorio.getCardById(cardId);
        assertNotNull(updatedCard);
        assertEquals("Updated Question", updatedCard.getPregunta());
        assertEquals("Updated Answer", updatedCard.getRespuesta());
        assertTrue(updatedCard.getUpdatedAt().isAfter(originalUpdatedAt));
    }

    @Test
    void updateCard_WithNonExistentId_ShouldThrowException() {
        // Arrange
        IFlashcard card = new Flashcard("Test Question", "Test Answer");
        // No podemos establecer el ID directamente, así que crearemos una tarjeta y luego la borraremos
        IFlashcard savedCard = repositorio.createCard(card);
        UUID cardId = savedCard.getId();
        repositorio.deleteCard(cardId);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> repositorio.updateCard(savedCard));
    }

    @Test
    void deleteCard_ShouldRemoveCard() {
        // Arrange
        IFlashcard card = new Flashcard("Test Question", "Test Answer");
        IFlashcard savedCard = repositorio.createCard(card);
        UUID cardId = savedCard.getId();

        // Act
        repositorio.deleteCard(cardId);

        // Assert
        assertNull(repositorio.getCardById(cardId));
    }

    @Test
    void deleteCard_WithNonExistentId_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> repositorio.deleteCard(UUID.randomUUID()));
    }

    @Test
    void createMultipleCards_ShouldPersistAllCards() {
        // Arrange
        IFlashcard card1 = new Flashcard("Question 1", "Answer 1");
        IFlashcard card2 = new Flashcard("Question 2", "Answer 2");
        
        // Act
        IFlashcard savedCard1 = repositorio.createCard(card1);
        IFlashcard savedCard2 = repositorio.createCard(card2);
        
        // Assert
        IFlashcard retrievedCard1 = repositorio.getCardById(savedCard1.getId());
        IFlashcard retrievedCard2 = repositorio.getCardById(savedCard2.getId());
        
        assertNotNull(retrievedCard1);
        assertNotNull(retrievedCard2);
        assertEquals("Question 1", retrievedCard1.getPregunta());
        assertEquals("Question 2", retrievedCard2.getPregunta());
    }

    @Test
    void updateCard_ShouldPreserveOtherCards() {
        // Arrange
        IFlashcard card1 = new Flashcard("Question 1", "Answer 1");
        IFlashcard card2 = new Flashcard("Question 2", "Answer 2");
        IFlashcard savedCard1 = repositorio.createCard(card1);
        IFlashcard savedCard2 = repositorio.createCard(card2);
        
        // Act
        savedCard1.setPregunta("Updated Question 1");
        repositorio.updateCard(savedCard1);
        
        // Assert
        IFlashcard retrievedCard1 = repositorio.getCardById(savedCard1.getId());
        IFlashcard retrievedCard2 = repositorio.getCardById(savedCard2.getId());
        
        assertEquals("Updated Question 1", retrievedCard1.getPregunta());
        assertEquals("Question 2", retrievedCard2.getPregunta());
    }

    @Test
    void showJsonExample() throws IOException {
        // Crear una flashcard de ejemplo
        IFlashcard card = new Flashcard("¿Qué es Java?", "Un lenguaje de programación orientado a objetos");
        
        // Guardar la card
        IFlashcard savedCard = repositorio.createCard(card);
        
        // Leer el contenido del archivo JSON
        String jsonContent = Files.readString(testFile.toPath());
        
        // Mostrar el JSON por consola
        System.out.println("\n=== JSON Generado ===");
        System.out.println(jsonContent);
        System.out.println("===================\n");
        
        // Verificar que el JSON contiene los datos correctos
        assertTrue(jsonContent.contains("¿Qué es Java?"));
        assertTrue(jsonContent.contains("Un lenguaje de programación orientado a objetos"));
    }
}
