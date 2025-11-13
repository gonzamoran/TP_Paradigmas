package edu.fiuba.algo3.modelo.excepciones;

public class ConstruccionPosInvalidaException extends RuntimeException {
    public ConstruccionPosInvalidaException(){
        super("No se puede construir en esa posicion");
    }
    public ConstruccionPosInvalidaException(String mensaje){
        super(mensaje);
    }
    
}
