package edu.fiuba.algo3.entrega_2.casosDeUso;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Construccion;


public class CasoDeUsoConstruccion {
    private Tablero tablero;
    private Jugador jugador;
    
    public CasoDeUsoConstruccion(Tablero tablero, Jugador jugador) {
        this.tablero = tablero;
        this.jugador = jugador;
    }

    public void construirEn(Coordenadas coordenadas, Construccion construccion) {
        tablero.colocarEn(coordenadas, construccion, jugador);
    }
}
