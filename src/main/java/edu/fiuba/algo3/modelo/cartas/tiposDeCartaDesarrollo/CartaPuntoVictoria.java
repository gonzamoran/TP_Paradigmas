package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;
import edu.fiuba.algo3.modelo.tablero.Ladron;

public class CartaPuntoVictoria extends CartasDesarrollo {

    public CartaPuntoVictoria() {
        super();
    }

    public CartaPuntoVictoria(int turnoDeCompra) {
        super(turnoDeCompra);
    }

    @Override
    public boolean esJugable(ContextoCartaDesarrollo contexto) {
        return false;
    }

    public CartasDesarrollo comprarCarta(int turnoActual) {
        return new CartaPuntoVictoria(turnoActual);
    }


    public int conseguirPV() {
        return 1;
    }

    @Override
    public void usar(ContextoCartaDesarrollo contexto, ProveedorDeDatos proveedor) {
       return;
    }
}