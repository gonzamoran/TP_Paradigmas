package edu.fiuba.algo3.modelo.tablero;

import java.util.Random;

import edu.fiuba.algo3.modelo.Jugador;

public class Ladron {
    private Hexagono hexagonoActual;

    public void moverA(Hexagono hexagono) {
        hexagonoActual = hexagono;
    }

    public void robarRecurso(Jugador desde, Jugador hacia) {

    }

    public void moverLadronA(Hexagono hexagono) {
        this.hexagonoActual.sacarLadron();
        this.hexagonoActual = hexagono;
        this.hexagonoActual.colocarLadron();
    }

    public Hexagono obtenerHexagonoActual() {
        return this.hexagonoActual;
    }
}
