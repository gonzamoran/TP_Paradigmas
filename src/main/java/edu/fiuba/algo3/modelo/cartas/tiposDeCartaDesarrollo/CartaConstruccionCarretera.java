package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.excepciones.NoEsPosibleConstruirException;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;

import edu.fiuba.algo3.modelo.excepciones.*;

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
    public void usar(ContextoCartaDesarrollo contexto, ProveedorDeDatos proveedor) {
        this.fueUsada = true;

        if (!contexto.sePuedeJugarCarta(this.turnoDeCompra)) {
            throw new NoSePuedeJugarEstaCartaException();
        }

        Jugador jugador = contexto.conseguirJugadorQueUsaLaCarta();
        Tablero tablero = contexto.obtenerTablero();

        int caminosColocados = 0;

        while (caminosColocados < 2) {
            Coordenadas origen = proveedor.pedirCoordenadasAlUsuario();
            Coordenadas destino = proveedor.pedirCoordenadasAlUsuario();
            if (!tablero.sonCoordenadasValidas(origen) || !tablero.sonCoordenadasValidas(destino)) {
                throw new CoordenadasInvalidasException();
                // continue;
            }

            tablero.construirCarreteraGratis(origen, destino, jugador);
            caminosColocados++;
        }
    }
}
