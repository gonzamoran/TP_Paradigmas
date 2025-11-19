package edu.fiuba.algo3.modelo.construcciones;

import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import java.util.List;

public class Poblado extends Construccion {
   
    public Poblado(String jugador){
        this.jugador = jugador;
        this.puntosVictoria = 1;
    }

    public int obtenerPuntosDeVictoria(){
        return this.puntosVictoria;
    }
    
    public boolean esPoblado() {
        return true;
    }
}