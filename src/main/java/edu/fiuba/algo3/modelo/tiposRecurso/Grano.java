package edu.fiuba.algo3.modelo.tiposRecurso;

import edu.fiuba.algo3.modelo.Recurso;
public class Grano extends Recurso {
    public Grano(){
        super();
    }
    public Grano(int cantidad) {
        super(cantidad);
    }

    public Recurso obtenerCopia(int cantidad) {
        return new Grano(cantidad);
    }
}
