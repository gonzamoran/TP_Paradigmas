package edu.fiuba.algo3.modelo.tiposRecurso;

import edu.fiuba.algo3.modelo.Recurso;

public class Ladrillo extends Recurso {
    public Ladrillo(){
        super();
    }   
    public Ladrillo(int cantidad) {
        super(cantidad);
    }

    public Recurso obtenerCopia(int cantidad) {
        return new Ladrillo(cantidad);
    }
}