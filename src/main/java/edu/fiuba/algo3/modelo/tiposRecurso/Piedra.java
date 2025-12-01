package edu.fiuba.algo3.modelo.tiposRecurso;

import edu.fiuba.algo3.modelo.Recurso;

public class Piedra extends Recurso {
    public Piedra(){
        super();
    }
    public Piedra(int cantidad) {
        super(cantidad);
    }

    public Recurso obtenerCopia(int cantidad) {
        return new Piedra(cantidad);
    }
}
