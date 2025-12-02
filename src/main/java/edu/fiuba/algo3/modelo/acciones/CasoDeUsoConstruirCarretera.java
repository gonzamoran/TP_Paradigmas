package edu.fiuba.algo3.modelo.acciones;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Construccion;

public class CasoDeUsoConstruirCarretera {
    private Tablero tablero;
    private Jugador jugador;

    public CasoDeUsoConstruirCarretera(Tablero tablero, Jugador jugador) {
        this.tablero = tablero;
        this.jugador = jugador;
    }

    public void construirCarretera(Coordenadas extremo1, Coordenadas extremo2) {
        tablero.construirCarretera(extremo1, extremo2, jugador);
    }

    public void construirEn(Coordenadas coordenadas, Construccion construccion) {
        tablero.colocarEn(coordenadas, construccion, jugador);
    }
}
