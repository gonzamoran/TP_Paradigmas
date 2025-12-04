package edu.fiuba.algo3.modelo.acciones;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.Construccion;

import java.util.ArrayList;
public class CasoDeUsoConstruccion {
    private Tablero tablero;
    private ArrayList<Jugador> jugadores;
    
    public CasoDeUsoConstruccion(Tablero tablero, ArrayList<Jugador> jugadores) {
        this.tablero = tablero;
        this.jugadores = jugadores;
    }

    public void construirEn(Coordenadas coordenadas, Construccion construccion, Jugador jugador) {
        tablero.colocarEn(coordenadas, construccion, jugador);
        
        for (Jugador unJugador : jugadores) {
            int longitud = tablero.obtenerCarreteraMasLarga(unJugador);
            unJugador.actualizarCaminoMasLargoDelJugador(longitud);
        }

        for (Jugador otroJugador : jugadores) {
            tablero.gestionarCaminoMasLargo(otroJugador);
        }
    }

    public void construirCarretera(Coordenadas extremo1, Coordenadas extremo2, Jugador jugador) {
        tablero.construirCarretera(extremo1, extremo2, jugador);
        int longitud = tablero.obtenerCarreteraMasLarga(jugador);
        jugador.actualizarCaminoMasLargoDelJugador(longitud);
        tablero.gestionarCaminoMasLargo(jugador);
    }
}
