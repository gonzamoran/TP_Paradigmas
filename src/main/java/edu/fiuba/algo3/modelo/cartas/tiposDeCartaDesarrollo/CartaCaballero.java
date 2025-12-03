package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.ContextoCartaDesarrollo;

import java.util.ArrayList;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;

import edu.fiuba.algo3.modelo.excepciones.CoordenadasInvalidasException;

/*
 * Mover el Ladrón y robar un recurso de un jugador
 * adyacente (similar a un 7, pero sin descarte de
 * cartas). Contribuyen a la Gran Caballería.
 * 
 */
public class CartaCaballero extends CartasDesarrollo {

    public CartaCaballero() {
        super();
    }

    public CartaCaballero(int turnoDeCompra) {
        super(turnoDeCompra);
    }

    public void usar(ContextoCartaDesarrollo contexto, ProveedorDeDatos proveedor) {
        this.fueUsada = true;
        Coordenadas coordDestino = proveedor.pedirCoordenadasAlUsuario(); // dentro tiene que validad que sean
                                                                          // coordenadas validas

        Tablero tablero = contexto.obtenerTablero();
        if (!tablero.sonCoordenadasValidas(coordDestino)) {
            throw new CoordenadasInvalidasException();
        }

        tablero.moverLadronA(coordDestino);
        Jugador jugador = contexto.conseguirJugadorQueUsaLaCarta();
        Jugador victima = proveedor.pedirJugadorARobar(contexto.conseguirJugadoresAfectados());
        tablero.ladronRobaRecurso(jugador); // tablero.ladronRobaRecurso(victima) PARA QUE ROBE A ALGUIEN EN ESPECIFICO

        jugador.sumarCaballero();
        if (jugador.obtenerCantidadCaballerosUsados() >= 3) {
            this.pasarGranCaballeria(contexto);
        }
    }

    public void pasarGranCaballeria(ContextoCartaDesarrollo contexto) {
        Jugador jugador = contexto.conseguirJugadorQueUsaLaCarta();
        ArrayList<Jugador> jugadores = contexto.conseguirJugadoresAfectados();
        int max = jugador.obtenerCantidadCaballerosUsados();
        Jugador jugadorConMasCaballeros = jugador;
        for (Jugador jugadorActual : jugadores) {
            int cantidad = jugadorActual.obtenerCantidadCaballerosUsados();
            if (cantidad > max) {
                jugadorConMasCaballeros = jugadorActual;
                max = cantidad;
            }
        }
        if (max >= 3) {
            jugadorConMasCaballeros.otorgarGranCaballeria(jugadores);
        }
    }

    public CartasDesarrollo comprarCarta(int turnoActual) {
        return new CartaCaballero(turnoActual);
    }

}
