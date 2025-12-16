package edu.fiuba.algo3.modelo.tablero;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;


public class Ladron {
    private Hexagono hexagonoActual;

    public Ladron(Hexagono hexagonoInicial) {
        this.hexagonoActual = hexagonoInicial;
    }

    public void moverLadronA(Hexagono hexagono) {
        this.hexagonoActual.sacarLadron();
        this.hexagonoActual = hexagono;
        this.hexagonoActual.colocarLadron();
    }
    
    public void robarRecurso(Jugador jugadorActual, Jugador jugadorARobar) {
        Recurso recursoRobado = null;
        if (jugadorARobar.tieneRecursos()) {
            Recurso robado = jugadorARobar.removerRecursoAleatorio();
            recursoRobado = robado;
        }
        jugadorActual.agregarRecurso(recursoRobado);
    }

    public Hexagono obtenerHexagonoActual() {
        return this.hexagonoActual;
    }
}