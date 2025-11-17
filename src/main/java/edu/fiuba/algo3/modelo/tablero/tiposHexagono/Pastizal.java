package edu.fiuba.algo3.modelo.tablero.tiposHexagono;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tablero.Hexagono;

public class Pastizal extends Hexagono {
    private String recurso;

    public Pastizal() {
        super();
        this.recurso = "Lana";
    }
    
    @Override
    public Recurso generarRecurso(int cantidad) {
        return Recurso.generarRecurso(this.recurso, cantidad);
    }

}