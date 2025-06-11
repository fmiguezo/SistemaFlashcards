package edu.utn.infrastructure.adapters.in.rest.controller;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;

public class WebUserPracticeController implements IUserPracticeInputPort {
    @Override
    public boolean askUserForAnswer(IFlashcard flashcard) {
        return false;
    }

    @Override
    public void showQuestion(IFlashcard flashcard) {

    }

    @Override
    public void showAnswer(IFlashcard flashcard) {

    }
}
