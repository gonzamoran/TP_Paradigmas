package edu.fiuba.algo3.modelo.tablero;

import java.util.Random;

import edu.fiuba.algo3.modelo.Jugador;

public class Ladron {
    private Hexagono hexagonoActual;

    public Ladron(Hexagono hexagonoInicial) {
        this.hexagonoActual = hexagonoInicial;
    }

    public void moverLadronA(Hexagono hexagono) {
        this.hexagonoActual.sacarLadron();
        this.hexagonoActual = hexagono;
        this.hexagonoActual.colocarLadron();
        this.robarRecurso();
    }

    public Hexagono obtenerHexagonoActual() {
        return this.hexagonoActual;
    }
    
    public void robarRecurso(Jugador desde, Jugador hacia) {

    }
}
