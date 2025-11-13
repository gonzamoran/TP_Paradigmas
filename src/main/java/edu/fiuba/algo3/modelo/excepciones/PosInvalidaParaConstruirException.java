package edu.fiuba.algo3.modelo.excepciones;


public class PosInvalidaParaConstruirException extends RuntimeException{
    public PosInvalidaParaConstruirException(){
        super("No se puede construir en esta posicion.");
    }
    public PosInvalidaParaConstruirException(String mensaje){
        super(mensaje);
    }
}