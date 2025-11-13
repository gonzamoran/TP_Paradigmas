package edu.fiuba.algo3.modelo.construcciones;

import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import java.util.List;

class Ciudad extends Construccion {

    public int obtenerPuntosDeVictoria(){
        return 2;
    }

    public boolean puedeConstruirse(Jugador jugador, Vertice vertice){
        if (!vertice.tieneConstruccion()) {
            return false;
        }

        if (vertice.esPoseidoPor(jugador) && vertice.esPoblado()) {
            return true;
        }
        return false;
    }

    public boolean esPoblado() {
        return false;
    }
}