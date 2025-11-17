package edu.fiuba.algo3.entrega_1.casosDeUso;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;

import java.util.ArrayList;

public class CasoDeUsoArmarTablero {
    private ArrayList<Hexagono> listaHexagonos;
    private ArrayList<Produccion> listaNumeros;
    private Tablero tablero;

    public CasoDeUsoArmarTablero(ArrayList<Hexagono> listaHexagonos, ArrayList<Produccion> listaNumeros) {
        this.listaHexagonos = listaHexagonos;
        this.listaNumeros = listaNumeros;
    }

    public Tablero armarTablero() {
        this.tablero = new Tablero(listaHexagonos, listaNumeros);
        return this.tablero;
    }

    public void mezclarNumeros() {
        tablero.mezclarNumeros();
    }

    public void mezclarHexagonos() {
        tablero.mezclarHexagonos();
    }

    public void mezclarTablero() {
        this.mezclarHexagonos();
        this.mezclarNumeros();
    }
}