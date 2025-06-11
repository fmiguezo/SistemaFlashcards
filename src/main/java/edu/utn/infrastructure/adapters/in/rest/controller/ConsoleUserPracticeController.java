package edu.utn.infrastructure.adapters.in.rest.controller;

import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;

import java.util.Scanner;

public class ConsoleUserPracticeController implements IUserPracticeInputPort {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void showQuestion(IFlashcard flashcard) {
        System.out.println("Pregunta: " + flashcard.getPregunta());
        String input = "";
        while (!input.equals("r")) {
            System.out.println("Presiona la tecla R para ver la respuesta.");
            input = scanner.nextLine().trim().toLowerCase();
        }
    }

    @Override
    public void showAnswer(IFlashcard flashcard) {
        System.out.println("Respuesta: " + flashcard.getRespuesta());
    }

    @Override
    public boolean askUserForAnswer(IFlashcard flashcard) {
        System.out.print("¿Respondiste correctamente? (s/n): ");
        String input = scanner.nextLine().trim().toLowerCase();
        return input.equals("s");
    }
}
