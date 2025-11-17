package edu.fiuba.algo3.modelo.construcciones;

import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import java.util.List;

class Poblado extends Construccion {
    public int obtenerPuntosDeVictoria(){
        return 1;
    }

    public boolean puedeConstruirse(Jugador jugador, Vertice vertice){
        if (vertice.tieneConstruccion()) {
            return false;
        }
        return true;
    }

    public boolean esPoblado() {
        return true;
    }
}