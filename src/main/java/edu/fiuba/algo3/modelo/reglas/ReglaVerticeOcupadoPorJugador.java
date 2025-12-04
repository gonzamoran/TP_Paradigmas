package edu.fiuba.algo3.modelo.reglas;


import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.construcciones.Construccion;

import edu.fiuba.algo3.modelo.excepciones.*;

public class ReglaVerticeOcupadoPorJugador implements ReglasConstruccion {

    @Override
    public boolean validar(Vertice vertice, Construccion construccion, Jugador jugador){
        if(!vertice.tieneConstruccion() || !vertice.esDueno(jugador)){
            return false;
        }
        return true;
    }
}
