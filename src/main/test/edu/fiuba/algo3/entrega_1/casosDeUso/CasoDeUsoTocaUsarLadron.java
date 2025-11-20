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
    private Coordenadas hexagonoActual;
    private Coordenadas hexagonoDestino;

    public CasoDeUsoTocaUsarLadron(Tablero tablero, Jugador jugador, Coordenadas hexagonoInicio) {
        this.tablero = tablero;
        this.jugador = jugador;
        tablero.moverLadronA(hexagonoInicio);
        this.hexagonoActual = hexagonoInicio;
    }

    public void configurarDestino(Coordenadas hexagonoDestino) {
        this.hexagonoDestino = hexagonoDestino;
    }

    public void lanzarDado(Dados dado) {
        int resultado = dado.lanzarDados();
        if (resultado == 7) {
            this.ladronRobaRecursos(); ///descarte
            this.moverLadronA(hexagonoDestino); //mover ladron
            this.robarCartas(jugador);
        }
    }

    public void moverLadronA(Coordenadas coordenadas) {
        tablero.moverLadronA(coordenadas);
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

    public void robarCartas(Jugador jugador){
        tablero.ladronRobaRecurso(jugador);
    }
}
