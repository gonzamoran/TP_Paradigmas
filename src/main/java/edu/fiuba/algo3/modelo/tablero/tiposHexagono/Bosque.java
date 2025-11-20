package edu.fiuba.algo3.modelo.tablero.tiposHexagono;

import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.Madera;

public class Bosque extends Hexagono {
    @Override
    public Recurso generarRecurso(int cantidad) {
        return new Madera(cantidad);
    }
}
