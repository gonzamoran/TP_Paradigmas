package edu.fiuba.algo3.modelo.reglas;


import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.construcciones.Construccion;

import edu.fiuba.algo3.modelo.excepciones.*;


public class ReglaDistancia implements ReglasConstruccion {

    @Override
    public boolean validar(Vertice vertice, Construccion construccion, Jugador jugadro) {
        if (construccion == null || !vertice.cumpleReglaDistancia()){
            return false;
        }
        return true;
    }
}