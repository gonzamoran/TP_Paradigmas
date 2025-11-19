package edu.fiuba.algo3.modelo.tablero.tiposHexagono;

import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.Recurso;

public class Bosque extends Hexagono {

    private String recurso;

    public Bosque() {
        super();
        this.recurso = "Madera";
    }

    @Override
    public Recurso generarRecurso(int cantidad) {
        return Recurso.generarRecurso(this.recurso, cantidad);
    }
}
