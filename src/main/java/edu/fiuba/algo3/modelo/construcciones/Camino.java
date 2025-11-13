package edu.fiuba.algo3.modelo.construcciones;
import java.util.List;
import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;


import edu.fiuba.algo3.modelo.*;
class Camino extends Construccion {

    public int obtenerPuntosDeVictoria(){
        return 0;
    }

    public boolean puedeConstruirse(Jugador jugador, Vertice vertice, List<Vertice> adyacentes, List<Construccion> construccionesAdyacentes){
        if (vertice.estaConstruido()) {
            return false;
        }
        for (Vertice adyacente : adyacentes) {
            if (adyacente.esPoseidoPor(jugador)) {
                return true;
            }
        }
        return false;
    }

    public boolean esPoblado() {
        return true;
    }
    public boolean esCiudad() {
        return false;
    }
    
}
