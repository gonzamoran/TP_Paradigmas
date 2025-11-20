package edu.fiuba.algo3.modelo.tiposRecurso;

import edu.fiuba.algo3.modelo.Recurso;

public class Nulo extends Recurso {
    public Nulo() {
        this.cantidad = 0;
    }

    public Recurso obtenerCopia(int cantidad) {
        return new Nulo();
    }
}
