package edu.fiuba.algo3.modelo.excepciones;

public class NoPuedeColocarConstruccionIniciales extends RuntimeException {
    public NoPuedeColocarConstruccionIniciales(){
        super("No se puede colocar la construccion inicial en esa posicion");
    }
    public NoPuedeColocarConstruccionIniciales(String mensaje){
        super(mensaje);
    }
}
