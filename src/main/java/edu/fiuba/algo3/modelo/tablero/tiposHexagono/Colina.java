package edu.fiuba.algo3.modelo.tablero.tiposHexagono;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tablero.Hexagono;

public class Colina extends Hexagono {
    private String recurso;

    public Colina() {
        super();
        this.recurso = "Ladrillo";
    }
    
    @Override
    public Recurso generarRecurso(int cantidad) {
        return Recurso.generarRecurso(this.recurso, cantidad);
    }

}