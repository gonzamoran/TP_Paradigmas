package edu.fiuba.algo3.modelo.excepciones;

public class MazoVacioException extends RuntimeException {
    public MazoVacioException() {
        super("El mazo esta vacio.");
    }

    public MazoVacioException(String mensaje) {
        super(mensaje);
    }
}
