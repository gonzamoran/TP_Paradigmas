package edu.fiuba.algo3.modelo.excepciones;

public class NoSePuedeJugarEstaCartaException extends RuntimeException {
    public NoSePuedeJugarEstaCartaException() {
        super("No se puede jugar esta carta en este turno.");
    }
    public NoSePuedeJugarEstaCartaException(String mensaje) {
        super(mensaje);
    }
}
