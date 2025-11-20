package edu.fiuba.algo3.entrega_1.casosDeUso;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;
import edu.fiuba.algo3.modelo.Dados;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.cartas.CartasJugador;

public class CasoDeUsoTocaUsarLadron {
    private Tablero tablero;
    private String jugador;
    private Hexagono hexagonoActual;
    private int resultado = 7; // Para activar el ladron.
    private CartasJugador cartasJugador;

    public CasoDeUsoTocaUsarLadron(Tablero tablero, String jugador, Hexagono hexagonoInicio, CartasJugador cartasJugador) {
        this.tablero = tablero;
        this.jugador = jugador;
        this.cartasJugador = cartasJugador;
        tablero.moverLadronA(hexagonoInicio);
        this.hexagonoActual = hexagonoInicio;
    }

    public int lanzarDado() {
        return resultado;
    }

    public void moverLadronA(Hexagono hexagonoLadron) {
        tablero.moverLadronA(hexagonoLadron);
    }

    public void agregarRecursos(Recurso[] recursos) {
        for (Recurso recurso : recursos){
            cartasJugador.agregarRecursos(recurso);
        }
    }

    public void ladronRobaRecursos() {
        if (!cartasJugador.puedeDescartarse()) {
            return;
        }
        cartasJugador.descarteCartas();
    }

}
