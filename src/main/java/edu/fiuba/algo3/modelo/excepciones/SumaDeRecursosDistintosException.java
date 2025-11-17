package edu.fiuba.algo3.modelo.excepciones;

public class SumaDeRecursosDistintosException extends RuntimeException {
    public SumaDeRecursosDistintosException(){
        super("No se puede sumar recursos distintos.");
    }
    public SumaDeRecursosDistintosException(String mensaje){
        super(mensaje);
    }
}
