package edu.fiuba.algo3.modelo.tablero.tiposHexagono;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tiposRecurso.Grano;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
public class Campo extends Hexagono {
    @Override
    public Recurso generarRecurso(int cantidad) {
        return new Grano(cantidad);
    }

}