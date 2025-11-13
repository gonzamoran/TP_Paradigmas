package edu.fiuba.algo3.modelo.construcciones;

import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import java.util.List;

class Ciudad extends Construccion {

    public int obtenerPuntosDeVictoria(){
        return 2;
    }

    public boolean puedeConstruirse(Jugador jugador, Vertice vertice, List<Vertice> adyacentes, List<Construccion> construccionesAdyacentes){
        if (!vertice.estaConstruido()) {
            return false;
        }

        if (vertice.esPoseidoPor(jugador) && vertice.esPoblado()) {
            return true;
        }

        return false;
    }
    
    public boolean esCiudad() {
        return true;
    }

    public boolean esPoblado() {
        return false;
    }
}