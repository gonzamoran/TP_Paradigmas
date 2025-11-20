package edu.fiuba.algo3.modelo.excepciones;

public class ErrorAlMejorarConstruccionException extends RuntimeException {
    public ErrorAlMejorarConstruccionException(){
        super("No se puede mejorar la construccion.");
    }
    public ErrorAlMejorarConstruccionException(String mensaje){
        super(mensaje);
    }
}