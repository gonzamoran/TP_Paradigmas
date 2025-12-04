package edu.fiuba.algo3.modelo.tablero;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;
import edu.fiuba.algo3.modelo.Recurso;

import java.util.ArrayList;

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
    
    public void robarRecurso(Jugador jugadorActual, ArrayList<Jugador> jugadoresAfectados, ProveedorDeDatos proveedor) {
        Recurso recursoRobado = null;
        if (jugadoresAfectados == null || jugadoresAfectados.isEmpty()) {
            return;
        }

        ArrayList<Jugador> candidatos = new ArrayList<>(jugadoresAfectados);
        candidatos.removeIf(j -> j.equals(jugadorActual));
        if (candidatos.isEmpty()) {
            return;
        }
        Jugador jugadorARobar = proveedor.pedirJugadorARobar(candidatos);
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