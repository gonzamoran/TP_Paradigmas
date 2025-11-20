package edu.fiuba.algo3.modelo.tiposRecurso;
import edu.fiuba.algo3.modelo.Recurso;

public class Lana extends Recurso {
    public Lana(int cantidad) {
        this.cantidad = cantidad;
    }

    public Recurso obtenerCopia(int cantidad) {
        return new Lana(cantidad);
    }
}
