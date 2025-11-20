package edu.fiuba.algo3.entrega_1.casosDeUso;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;
import edu.fiuba.algo3.modelo.Dados;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.cartas.CartasJugador;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;

public class CasoDeUsoTocaUsarLadron {
    private Tablero tablero;
    private Jugador jugador;
    private Hexagono hexagonoActual;
    private Hexagono hexagonoDestino;

    public CasoDeUsoTocaUsarLadron(Tablero tablero, Jugador jugador, Hexagono hexagonoInicio) {
        this.tablero = tablero;
        this.jugador = jugador;
        tablero.moverLadronA(hexagonoInicio);
        this.hexagonoActual = hexagonoInicio;
    }

    public void configurarDestino(Hexagono hexagonoDestino) {
        this.hexagonoDestino = hexagonoDestino;
    }

    public void lanzarDado(Dados dado) {
        int resultado = dado.lanzarDados();
        if (resultado == 7) {
            this.ladronRobaRecursos(); ///descarte
            this.moverLadronA(); //mover ladron y robar cartas
            this.robarCartas();
        }
    }

    public void moverLadronA() {
        tablero.moverLadronA(hexagonoDestino);
        this.hexagonoActual = hexagonoDestino;
    }

    public void agregarRecursos(Recurso[] recursos) {
        for (Recurso recurso : recursos){
            jugador.agregarRecurso(recurso);
        }
    }

    public void ladronRobaRecursos() {
        if (!jugador.puedeDescartarse()) {
            return;
        }
        jugador.descartarse();
    }
    public void colocarEn(Coordenadas coordenadas, Construccion construccion, Jugador jugador){
        tablero.colocarEn(coordenadas,construccion, jugador);
    }

    public void robarCartas(Jugador jugador1, Jugador jugador2){
        // Mover 
        // Segundo coordenada nueva del ladron sea igual a la coordenada del jugador a robar.

    }
}
