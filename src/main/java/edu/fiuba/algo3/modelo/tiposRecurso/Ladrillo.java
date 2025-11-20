package edu.fiuba.algo3.modelo.tiposRecurso;

import edu.fiuba.algo3.modelo.Recurso;

public class Ladrillo extends Recurso {
    public Ladrillo(int cantidad) {
        this.cantidad = cantidad;
    }

    public Recurso obtenerCopia(int cantidad) {
        return new Ladrillo(cantidad);
    }
}