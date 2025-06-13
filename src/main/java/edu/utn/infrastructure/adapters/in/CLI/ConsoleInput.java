package edu.utn.infrastructure.adapters.in.CLI;

import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput implements IUserPracticeInputPort {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void showQuestion(IFlashcard flashcard) {
        System.out.println("Pregunta: " + flashcard.getPregunta());
        String input = "";
        while (!input.equals("1")) {
            System.out.println("Presiona la tecla 1 para ver la respuesta.");
            input = scanner.nextLine().trim().toLowerCase();
        }
    }

    @Override
    public void showAnswer(IFlashcard flashcard) {
        System.out.println("Respuesta: " + flashcard.getRespuesta());
    }

    @Override
    public boolean askUserForAnswer(IFlashcard flashcard) {
        System.out.print("¿Respondiste correctamente? Presioná 1 para sí, 2 para no: ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("1");
    }
}
