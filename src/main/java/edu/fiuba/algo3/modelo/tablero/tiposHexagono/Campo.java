package edu.fiuba.algo3.modelo.tablero.tiposHexagono;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
public class Campo extends Hexagono {
    private String recurso;

    public Campo() {
        super();
        this.recurso = "Grano";
    }
    
    @Override
    public Recurso generarRecurso(int cantidad) {
        return Recurso.generarRecurso(this.recurso, cantidad);
    }

}
