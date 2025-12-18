package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.excepciones.CoordenadasInvalidasException;
import edu.fiuba.algo3.modelo.excepciones.NoSePuedeJugarEstaCartaException;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Tablero;


import java.util.ArrayList;
import java.util.List;

/*
 * Permite construir 2 Carreteras gratuitamente (debe
 * cumplir reglas de colocación)
 * 
 */
public class CartaConstruccionCarretera extends CartasDesarrollo {

    public CartaConstruccionCarretera() {
        super();
    }

    public CartaConstruccionCarretera(int turnoActual) {
        super(turnoActual);

    }

    public CartasDesarrollo comprarCarta(int turnoActual) {
        return new CartaConstruccionCarretera(turnoActual);
    }

    @Override
    public void usar(ContextoCartaDesarrollo contexto) {
        this.fueUsada = true;

        if (!contexto.sePuedeJugarCarta(this.turnoDeCompra)) {
            throw new NoSePuedeJugarEstaCartaException();
        }

        Jugador jugador = contexto.conseguirJugadorQueUsaLaCarta();
        Tablero tablero = contexto.obtenerTablero();
        ArrayList<List<Coordenadas>> coordenadasCarreteras = contexto.obtenerCoordenadasCarreteras();

        for (List<Coordenadas> camino : coordenadasCarreteras) {
            Coordenadas origen = camino.get(0);
            Coordenadas destino = camino.get(1);
            if (!tablero.sonCoordenadasValidas(origen) || !tablero.sonCoordenadasValidas(destino)) {
                throw new CoordenadasInvalidasException();
            }
        }

        for (List<Coordenadas> camino : coordenadasCarreteras) {
            Coordenadas origen = camino.get(0);
            Coordenadas destino = camino.get(1);
            if (!tablero.puedeConstruirCarreteraGratis(origen, destino, jugador)) {
                throw new edu.fiuba.algo3.modelo.excepciones.NoEsPosibleConstruirException("No es posible construir todas las carreteras solicitadas");
            }
        }

        for (List<Coordenadas> camino : coordenadasCarreteras) {
            Coordenadas origen = camino.get(0);
            Coordenadas destino = camino.get(1);
            tablero.construirCarreteraGratis(origen, destino, jugador);
        }
    }
}
