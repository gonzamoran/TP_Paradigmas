package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Ladron;
import edu.fiuba.algo3.modelo.tablero.Tablero;

import java.util.ArrayList;
import java.util.List;

public class ContextoCartaDesarrollo {
    private Jugador jugadorQueUsaLaCarta;
    private ArrayList<Jugador> jugadores;
    private int turnoActual;
    private Tablero tablero;
    private Ladron ladron;
    private Coordenadas coordenadasOrigen;
    private Coordenadas coordenadasDestino;
    private Jugador jugadorObjetivo;
    private Recurso recursoElegido;
    private ArrayList<Recurso> recursosElegidos;
    private ArrayList<List<Coordenadas>> coordenadasCarreteras;

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

    public void establecerCoordenadasOrigen(Coordenadas origen) {
        this.coordenadasOrigen = origen;
    }

    public Coordenadas obtenerCoordenadasOrigen() {
        return this.coordenadasOrigen;
    }

    public void establecerCoordenadasDestino(Coordenadas destino) {
        this.coordenadasDestino = destino;
    }

    public Coordenadas obtenerCoordenadasDestino() {
        return this.coordenadasDestino;
    }

    public void establecerJugadorObjetivo(Jugador jugador) {
        this.jugadorObjetivo = jugador;
    }

    public Jugador obtenerJugadorARobar() {
        return this.jugadorObjetivo;
    }

    public void establecerRecursoElegido(Recurso recurso) {
        this.recursoElegido = recurso;
    }

    public Recurso obtenerRecursoElegido() {
        return this.recursoElegido;
    }

    public void establecerRecursosElegidos(ArrayList<Recurso> recursos) {
        this.recursosElegidos = recursos;
    }

    public ArrayList<Recurso> obtenerRecursosElegidos() {
        if (this.recursosElegidos == null) {
            return new ArrayList<>();
        }
        return this.recursosElegidos;
    }

    public void establecerCoordenadasCarreteras(ArrayList<List<Coordenadas>> coordenadasCarreteras) {
        this.coordenadasCarreteras = coordenadasCarreteras;
    }

    public ArrayList<List<Coordenadas>> obtenerCoordenadasCarreteras() {
        return this.coordenadasCarreteras;
    }
}
