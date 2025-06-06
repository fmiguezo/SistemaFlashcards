package edu.utn.infrastructure.adapters.out.persistence;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.utn.domain.model.Flashcard;
import edu.utn.domain.model.IFlashcard;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
@Profile("json") //Esto se activa cuando el perfil está en JSON
public class RepositorioDeCardsJson implements IFlashcardRepository {
    private final String jsonFilePath;
    private final ObjectMapper objectMapper;

    public RepositorioDeCardsJson(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    private void saveCards(List<IFlashcard> cards) {
        try {
            var rootNode = objectMapper.createObjectNode();
            rootNode.set("cards", objectMapper.valueToTree(cards));
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonFilePath), rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<IFlashcard> getAllCards() {
        try {
            File file = new File(jsonFilePath);
            if (!file.exists()) return new ArrayList<>();

            JsonNode rootNode = objectMapper.readTree(file);
            JsonNode cardsNode = rootNode.get("cards");

            List<IFlashcard> cards = new ArrayList<>();
            if (cardsNode != null && cardsNode.isArray()) {
                for (JsonNode cardNode : cardsNode) {
                    Flashcard card = objectMapper.treeToValue(cardNode, Flashcard.class);
                    cards.add(card);
                }
            }

            return cards;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public IFlashcard getCardById(UUID id) {
        return getAllCards().stream()
                .filter(card -> card.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public IFlashcard createCard(IFlashcard card) {
        List<IFlashcard> cards = getAllCards();
        cards.add(card);
        saveCards(cards);
        return card;
    }

    @Override
    public void updateCard(IFlashcard card) {
        List<IFlashcard> cards = getAllCards();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getId().equals(card.getId())) {
                cards.set(i, card);
                saveCards(cards);
                return;
            }
        }
        throw new IllegalArgumentException("No se encontró una flashcard con el ID: " + card.getId());
    }

    @Override
    public void deleteCard(UUID id) {
        List<IFlashcard> cards = getAllCards();
        boolean removed = cards.removeIf(card -> card.getId().equals(id));
        if (removed) {
            saveCards(cards);
        } else {
            throw new IllegalArgumentException("No se encontró una flashcard con el ID: " + id);
        }
    }
}
