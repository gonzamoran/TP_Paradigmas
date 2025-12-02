package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Hexagono;


import java.util.ArrayList;

public class ContextoCartaDesarrollo {
    private Jugador jugadorQueUsaLaCarta;
    private ArrayList<Jugador> jugadoresAfectados;
    private int turnoActual;
    private Recurso recursoSolicitado;
    private Tablero tablero;
    // private Coordenadas coordenadasDestino;
    // private Hexagono hexagonoDestino;

    public ContextoCartaDesarrollo(Jugador jugadorQueUsaLaCarta, ArrayList<Jugador> jugadoresAfectados,
            int turnoActual, Recurso recursoSolicitado, Tablero tablero) {
        this.jugadorQueUsaLaCarta = jugadorQueUsaLaCarta;
        this.jugadoresAfectados = jugadoresAfectados;
        this.turnoActual = turnoActual;
        this.recursoSolicitado = recursoSolicitado;
        this.tablero = tablero;
    }
    public Jugador conseguirJugadorQueUsaLaCarta() {
        return this.jugadorQueUsaLaCarta;
    }

    public Recurso obtenerRecursoSolicitado() {
        return this.recursoSolicitado;
    }

    public ArrayList<Jugador> conseguirJugadoresAfectados() {
        return this.jugadoresAfectados;
    }

    public boolean sePuedeJugarCarta(int turnoDeCompra) {
        return this.turnoActual > turnoDeCompra;
    }
    
    public void moverLadron(Coordenadas coordenadas) {
        this.tablero.moverLadronA(coordenadas);
    }

    public ArrayList<Recurso> robarA(){
        return this.tablero.ladronRobaRecurso(this.jugadorQueUsaLaCarta);
    }
}
