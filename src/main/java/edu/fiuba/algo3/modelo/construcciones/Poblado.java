package edu.fiuba.algo3.modelo.construcciones;

import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import java.util.List;

class Poblado extends Construccion {

    
    public int obtenerPuntosDeVictoria(){
        return 1;
    }

    public boolean puedeConstruirse(Jugador jugador, Vertice vertice, List<Vertice> adyacentes, List<Construccion> construccionesAdyacentes){
        if (vertice.estaConstruido()) {
            return false;
        }
        for (Construccion construccion : construccionesAdyacentes) {
            if (construccion.esPoblado() || construccion.esCiudad()) {
                return false;
            }
        }
        return true;
    }

    public boolean esPoblado() {
        return true;
    }

    public boolean esCiudad() {
        return false;
    }
}