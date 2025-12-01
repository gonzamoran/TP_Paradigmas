package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Ladron;

public class CartaPuntoVictoria extends CartasDesarrollo {

    public CartaPuntoVictoria() {
        super();
    }

    public CartaPuntoVictoria(int turnoDeCompra) {
        super(turnoDeCompra);
    }

    public boolean esJugable(ContextoCartaDesarrollo contexto) {
        return false;
    }

    public int conseguirPV() {
        return 1;
    }

    public void usar(ContextoCartaDesarrollo contexto) {
        return;
    }

    public CartasDesarrollo comprarCarta(int turnoActual) {
        return new CartaPuntoVictoria(turnoActual);
    }
}