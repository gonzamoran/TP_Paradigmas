package edu.fiuba.algo3.modelo.reglas;


import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.construcciones.Construccion;



public interface ReglasConstruccion {

    boolean validar(Vertice vertice, Construccion construccion, Jugador jugador);
}