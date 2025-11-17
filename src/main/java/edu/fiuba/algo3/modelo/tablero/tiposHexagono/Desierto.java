package edu.fiuba.algo3.modelo.tablero.tiposHexagono;

import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.Recurso;

public class Desierto extends Hexagono {

    public Desierto() {
        super();
    }

    @Override
    public boolean esDesierto() {
        return true;
    }

    @Override
    public boolean puedeGenerarRecursos() {
        return false;
    }

    @Override
    public Recurso generarRecurso(int cantidad) {
        return Recurso.generarRecurso("Nulo", 0);
    }
    
}
