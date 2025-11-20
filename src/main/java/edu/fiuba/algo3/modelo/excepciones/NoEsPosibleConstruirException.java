package edu.fiuba.algo3.modelo.excepciones;

public class NoEsPosibleConstruirException extends RuntimeException {
    public NoEsPosibleConstruirException(){
        super("No se cumplen los requisitos para construir.");
    }

    
    public NoEsPosibleConstruirException(String mensaje){
        super(mensaje);
    }
}
