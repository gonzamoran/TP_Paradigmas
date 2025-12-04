package edu.fiuba.algo3.modelo.tablero.tiposHexagono;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tiposRecurso.Nulo;

public class Desierto extends Hexagono {

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
        return new Nulo();
    }
    
}
