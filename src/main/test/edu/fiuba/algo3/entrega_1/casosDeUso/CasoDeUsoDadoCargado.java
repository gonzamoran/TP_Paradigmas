package edu.fiuba.algo3.entrega_1.casosDeUso;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.construcciones.*;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Dados;

import java.util.ArrayList;

public class CasoDeUsoDadoCargado {
    private int resultado = 8;
    private String jugador;
    private Tablero tablero;

    public CasoDeUsoDadoCargado(Tablero tablero, String nombreJugador) {
        this.tablero = tablero;
        this.jugador = nombreJugador;
    }

    public void colocarEn(Coordenadas coordenadas, Construccion construccion, String jugador) {
        if (coordenadas == null || (!tablero.sonCoordenadasValidas(coordenadas))) {
            throw new IllegalArgumentException("Coordenadas invalidas"); // cambiar excepcion
        }
        if (!tablero.sePuedeConstruir(coordenadas, construccion)) {
            throw new IllegalArgumentException("No se puede construir aqui"); // cambiar excepcion
        }
        tablero.colocarEn(coordenadas, construccion, this.jugador);
    }

    public ArrayList<Recurso> producirRecursos(int resultado) {
        return tablero.producirRecurso(resultado, jugador);
    }

    public int lanzarDados() {
        return resultado;
    }
    /*
     * public ArrayList<Recurso> producirRecursos(){
     * ArrayList<Recurso> recursosGenerados = new ArrayList<>();
     * ArrayList<Hexagono> hexagonos = tablero.obtenerHexagonosConNumero(resultado);
     * tablero.generarRecursos(produccion);
     * 
     * for (Hexagono hex: hexagonos){
     * int cantidad = si es ciudad 2 sino 1 //lo maneja construccion
     * 
     * recursosGenerados.add(Recurso.producirRecursos(hex, cantidad));
     * }
     * 
     * return recursosGenerados;
     * 
     * }
     */

}