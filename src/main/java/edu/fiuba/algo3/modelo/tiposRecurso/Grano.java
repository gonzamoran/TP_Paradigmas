package edu.fiuba.algo3.modelo.tiposRecurso;

import edu.fiuba.algo3.modelo.Recurso;
public class Grano extends Recurso {
    public Grano(String tipo, int cantidad) {
        this.cantidad = cantidad;
        this.tipo = tipo;
    }
}
