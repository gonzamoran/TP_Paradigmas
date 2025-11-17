package edu.fiuba.algo3.modelo.tablero.tiposHexagono;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tablero.Hexagono;

public class Montana extends Hexagono {
    private String recurso;

    public Montana() {
        super();
        this.recurso = "Piedra";
    }
    
    @Override
    public Recurso generarRecurso(int cantidad) {
        return Recurso.generarRecurso(this.recurso, cantidad);
    }

}