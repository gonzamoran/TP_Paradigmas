package edu.fiuba.algo3.modelo.tablero;

import edu.fiuba.algo3.modelo.Jugador;

public class Carretera {
    private Jugador jugador;
    private Coordenadas coordenadas;

    public Carretera(Jugador jugador, Coordenadas coordenadas){
        this.jugador = jugador;
        this.coordenadas = coordenadas;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Coordenadas getCoordenadas() {
        return coordenadas;
    }
}