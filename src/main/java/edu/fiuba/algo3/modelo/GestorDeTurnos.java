package edu.fiuba.algo3.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.acciones.*;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;

public class GestorDeTurnos {
    int turnoActual;
    ArrayList<Jugador> jugadores;
    int indiceJugadorActual;
    Tablero tablero;

    public GestorDeTurnos() {
        this.turnoActual = 0;
        this.indiceJugadorActual = 0;
    }

    public Jugador obtenerJugadorActual() {
        return jugadores.get(indiceJugadorActual);
    }

    public void avanzarTurno() {
        indiceJugadorActual = (indiceJugadorActual + 1) % jugadores.size();
        if (indiceJugadorActual == 0) {
            turnoActual++;
        }
    }

    public boolean terminoElJuego() {
        if (jugadores.get(indiceJugadorActual).calculoPuntosVictoria() >= 10) {
            return true;
        }
        return false;
    }

    public void iniciarTurno(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);

    }

    public void iniciarJuego(){
        ArrayList<Hexagono> hexagonos = new ArrayList<Hexagono>(List.of(
            new Desierto(),
            new Campo(), new Campo(), new Campo(), new Campo(),
            new Bosque(), new Bosque(), new Bosque(), new Bosque(),
            new Pastizal(), new Pastizal(), new Pastizal(), new Pastizal(),
            new Colina(), new Colina(), new Colina(),
            new Montana(), new Montana(), new Montana()
        ));
        ArrayList<Produccion> numeros = new ArrayList<Produccion>(List.of(
                new Produccion(2),
                new Produccion(3),
                new Produccion(3),
                new Produccion(4),
                new Produccion(4),
                new Produccion(5),
                new Produccion(5),
                new Produccion(6),
                new Produccion(6),
                new Produccion(8),
                new Produccion(8),
                new Produccion(9),
                new Produccion(9),
                new Produccion(10),
                new Produccion(10),
                new Produccion(11),
                new Produccion(11),
                new Produccion(12)
        ));
        Collections.shuffle(hexagonos);
        Collections.shuffle(numeros);
        CasoDeUsoArmarTablero casoTablero = new CasoDeUsoArmarTablero(hexagonos, numeros);
        Tablero tablero = casoTablero.armarTablero();
        this.tablero = tablero;
        //proveedor.pedirDatosJugadores();
    }
}
