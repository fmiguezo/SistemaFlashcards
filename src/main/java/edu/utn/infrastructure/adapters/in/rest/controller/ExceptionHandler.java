package edu.utn.infrastructure.adapters.in.rest.controller;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
    //*@RestControllerAdvice
    //Esto le dice a Spring:
    //“Esta clase es un manejador global de errores (y otras cosas),
    // compartido por todos los controllers.”
    //Es como un interceptor global de excepciones.
    //* @ExceptionHandler
    //Esta anotación le dice a Spring:
    //
    //“Si en cualquier parte de la app se lanza esta excepción,
    // usá este método para manejarla.”
    //Spring busca la primera clase con @RestControllerAdvice que tenga
    // un método marcado con @ExceptionHandler(TuExcepcion.class) y ejecuta
    // ese método automáticamente cuando detecta la excepción lanzada.

    // Lo uso para mantener limpio mi controlador y que maneje la excepciones que puedan suceder a la hora de hacer la
    // entrada de datos dentro del controlador.
    // Ejemplo de manejo de excepciones personalizadas
    //*    @ExceptionHandler(InvalidDeckException.class)
    //    public ResponseEntity<String> handleInvalidDeck(InvalidDeckException ex) {
    //        return ResponseEntity.badRequest().body("Deck inválido: " + ex.getMessage());
    //    }
    //
    //    @ExceptionHandler(Exception.class)
    //    public ResponseEntity<String> handleGeneral(Exception ex) {
    //        return ResponseEntity.status(500).body("Error interno: " + ex.getMessage());
    //    }

}
