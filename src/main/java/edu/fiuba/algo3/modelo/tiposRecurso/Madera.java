package edu.fiuba.algo3.modelo.tiposRecurso;

import edu.fiuba.algo3.modelo.Recurso;

public class Madera extends Recurso {

    public Madera(int cantidad) {
        this.cantidad = cantidad;
    }

    public Recurso obtenerCopia(int cantidad) {
        return new Madera(cantidad);
    }
}
