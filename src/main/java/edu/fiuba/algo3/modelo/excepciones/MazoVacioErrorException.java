package edu.fiuba.algo3.modelo.excepciones;

public class MazoVacioErrorException extends RuntimeException {
    public MazoVacioErrorException() {
        super("El mazo esta vacio.");
    }
}
