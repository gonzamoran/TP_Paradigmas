package edu.fiuba.algo3.entrega_1.casosDeUso;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.*;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Dados;
import edu.fiuba.algo3.modelo.Jugador;

import java.util.ArrayList;

public class CasoDeUsoDadoCargado {
    private int resultado = 8;
    private Jugador jugador;
    private Tablero tablero;

    public CasoDeUsoDadoCargado(Tablero tablero, Jugador nombreJugador) {
        this.tablero = tablero;
        this.jugador = nombreJugador;
    }

    public void colocarEn(Coordenadas coordenadas, Construccion construccion) {
        tablero.colocarEn(coordenadas, construccion, jugador);
    }

    private ArrayList<Recurso> producirRecursos(int nroProduccion) {
        return tablero.producirRecurso(resultado, jugador);
    }

    public ArrayList<Recurso> lanzarDados(Dados dado) {
        resultado = dado.lanzarDados();
        ArrayList<Recurso> recursos = this.producirRecursos(resultado);
        return recursos;
    }

}