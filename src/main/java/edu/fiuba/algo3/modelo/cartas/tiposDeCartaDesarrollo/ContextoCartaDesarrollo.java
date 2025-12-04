package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Ladron;

import java.util.ArrayList;

public class ContextoCartaDesarrollo {
    private Jugador jugadorQueUsaLaCarta;
    private ArrayList<Jugador> jugadores;
    private int turnoActual;
    private Tablero tablero;
    private Ladron ladron;

    public ContextoCartaDesarrollo(Jugador jugadorActual, ArrayList<Jugador> jugadores, int turnoActual,Tablero tablero, Ladron ladron) {
        this.jugadorQueUsaLaCarta = jugadorActual;
        this.turnoActual = turnoActual;
        this.tablero = tablero;
        this.jugadores = jugadores;
        this.ladron = ladron;
    }

    public Jugador conseguirJugadorQueUsaLaCarta() {
        return this.jugadorQueUsaLaCarta;
    }

    public ArrayList<Jugador> conseguirJugadoresAfectados() {
        return this.jugadores;
    }

    public int obtenerTurnoActual(){
        return this.turnoActual;
    }

    public boolean sePuedeJugarCarta(int turnoDeCompra) {
        return this.turnoActual > turnoDeCompra;
    }
    
    public Tablero obtenerTablero(){
        return this.tablero;
    }

    public Ladron obtenerLadron(){
        return this.ladron;
    }
}
