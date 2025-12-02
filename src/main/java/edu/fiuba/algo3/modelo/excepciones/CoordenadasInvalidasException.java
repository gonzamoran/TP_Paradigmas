package edu.fiuba.algo3.modelo.excepciones;

public class CoordenadasInvalidasException extends RuntimeException {
    
    public CoordenadasInvalidasException() {
        super();
    }

    public CoordenadasInvalidasException(String mensaje) {
        super(mensaje);
    }
}

