package edu.fiuba.algo3.modelo.construcciones;

import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import java.util.List;

public class Ciudad extends Construccion {

    public Ciudad(){
        this.puntosVictoria = 2;
    }

    public int obtenerPuntosDeVictoria(){
        return this.puntosVictoria;
    }
    
    public boolean esPoblado() {
        return false;
    }
}