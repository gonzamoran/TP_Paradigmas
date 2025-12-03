package edu.fiuba.algo3.modelo.tablero;

import java.util.Random;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;

import java.util.ArrayList;
import java.util.List;

public class Ladron {
    private Hexagono hexagonoActual;
    private List<Jugador> jugadoresAfectados;

    public Ladron(Hexagono hexagonoInicial) {
        this.hexagonoActual = hexagonoInicial;
        this.jugadoresAfectados = null;
    }

    public void moverLadronA(Hexagono hexagono, List<Jugador> jugadoresAfectados) {
        this.hexagonoActual.sacarLadron();
        this.hexagonoActual = hexagono;
        this.hexagonoActual.colocarLadron();
        this.jugadoresAfectados = jugadoresAfectados;
    }
    
    //roba un recurso a UN jugadorAfectado SALVO el recibido por parametro.
    public ArrayList<Recurso> robarRecurso(Jugador jugadorActual) {
        ArrayList<Recurso> recursoRobado = new ArrayList<Recurso>();
        if (jugadoresAfectados == null || jugadoresAfectados.isEmpty()) {
            return recursoRobado;
        }

        List<Jugador> candidatos = new java.util.ArrayList<>(jugadoresAfectados);
        candidatos.removeIf(j -> j.equals(jugadorActual));
        if (candidatos.isEmpty()) {
            return recursoRobado;
        }
        Random random = new Random();
        
        Jugador jugadorARobar = candidatos.get(random.nextInt(candidatos.size()));
        if (jugadorARobar.tieneRecursos()) {
            Recurso robado = jugadorARobar.removerRecursoAleatorio();
            recursoRobado.add(robado);
        }
        return recursoRobado;
    }

    public Hexagono obtenerHexagonoActual() {
        return this.hexagonoActual;
    }
}