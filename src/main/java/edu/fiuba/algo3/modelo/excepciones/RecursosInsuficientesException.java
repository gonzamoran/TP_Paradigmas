package edu.fiuba.algo3.modelo.excepciones;

public class RecursosInsuficientesException extends RuntimeException {
    public RecursosInsuficientesException() {
        super("El mazo esta vacio.");
    }

    public RecursosInsuficientesException(String mensaje) {
        super(mensaje);
    }
}
