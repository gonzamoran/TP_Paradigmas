package edu.fiuba.algo3.modelo.excepciones;

public class IntercambioInvalidoException extends RuntimeException {
    public IntercambioInvalidoException(){
        super("El intercambio no es valido entre los jugadores.");
    }
    public IntercambioInvalidoException(String mensaje){
        super(mensaje);
    }
}
