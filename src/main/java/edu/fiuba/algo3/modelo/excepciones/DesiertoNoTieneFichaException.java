package edu.fiuba.algo3.modelo.excepciones;


public class DesiertoNoTieneFichaException extends RuntimeException{
    public DesiertoNoTieneFichaException(){
        super("El desierto no tiene ficha asignada");
    }
    public DesiertoNoTieneFichaException(String mensaje){
        super(mensaje);
    }
}