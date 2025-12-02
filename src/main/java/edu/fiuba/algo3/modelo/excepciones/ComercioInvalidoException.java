package edu.fiuba.algo3.modelo.excepciones;

public class ComercioInvalidoException extends RuntimeException {
    public ComercioInvalidoException() {
        super("Comercio invalido.");
    }

    public ComercioInvalidoException(String mensaje) {
        super(mensaje);
    }
}
