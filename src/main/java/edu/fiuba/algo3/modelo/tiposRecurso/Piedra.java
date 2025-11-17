package edu.fiuba.algo3.modelo.tiposRecurso;

import edu.fiuba.algo3.modelo.Recurso;

public class Piedra extends Recurso {
    public Piedra(String tipo, int cantidad) {
        this.cantidad = cantidad;
        this.tipo = tipo;
    }
}
