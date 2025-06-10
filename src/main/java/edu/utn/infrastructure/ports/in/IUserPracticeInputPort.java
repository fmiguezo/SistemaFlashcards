package edu.utn.infrastructure.ports.in;

import edu.utn.domain.model.IFlashcard;

public interface IUserPracticeInputPort {
    boolean askUserForAnswer(IFlashcard flashcard);
    String showQuestion(IFlashcard flashcard);
    String showAnswer(IFlashcard flashcard);
    // Aca vamos a mover showquestion y showAnswer de flashcardService
    // practice flashcard tiene que recibir por parametro la interfaz de usuario.
    // Y segun la interfaz de usuario cada iUser practice va a hacer lo suyo
    // uno para web y otra para cli.
}
