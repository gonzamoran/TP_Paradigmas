package edu.fiuba.algo3.modelo.acciones;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;
import edu.fiuba.algo3.modelo.Dados;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.cartas.CartasJugador;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;

import edu.fiuba.algo3.modelo.ProveedorDeDatos;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CasoDeUsoGirarElDado {
    private Tablero tablero;
    private Jugador jugador;
    private Coordenadas hexagonoActual;
    private Coordenadas hexagonoDestino;

    public CasoDeUsoGirarElDado(Tablero tablero, Jugador jugador, Coordenadas hexagonoInicio) {
        this.tablero = tablero;
        this.jugador = jugador;
        this.hexagonoActual = hexagonoInicio;
        tablero.moverLadronA(hexagonoInicio);
    }

    public void configurarDestino(Coordenadas hexagonoDestino) {
        this.hexagonoDestino = hexagonoDestino;
    }

    public ArrayList<Recurso> lanzarDado(Dados dado, ProveedorDeDatos proveedor) {
        int resultado = dado.lanzarDados();
        if (resultado == 7) {
            jugador.descartarse();
            tablero.moverLadronA(hexagonoDestino);
            this.hexagonoActual = hexagonoDestino;
            return tablero.ladronRobaRecurso(jugador, proveedor);
        } else {
            return tablero.producirRecurso(resultado, jugador);
        }
    }

    public void colocarEn(Coordenadas coordenadas, Construccion construccion, Jugador jugadorConstructor){
        tablero.colocarEn(coordenadas,construccion, jugadorConstructor);
    }

}
