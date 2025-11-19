package edu.fiuba.algo3.modelo.tiposRecurso;

import edu.fiuba.algo3.modelo.Recurso;

public class Ladrillo extends Recurso {
    public Ladrillo(String tipo, int cantidad) {
        this.cantidad = cantidad;
        this.tipo = tipo;
    }
}