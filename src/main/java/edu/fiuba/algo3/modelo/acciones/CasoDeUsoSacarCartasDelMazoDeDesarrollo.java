package edu.fiuba.algo3.modelo.acciones;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;
import edu.fiuba.algo3.modelo.cartas.MazoCartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.ContextoCartaDesarrollo;

public class CasoDeUsoSacarCartasDelMazoDeDesarrollo {

    public void comprarCartaDesarrollo(MazoCartasDesarrollo mazo, Jugador jugador, int turnoActual) {
        var carta = mazo.sacarCarta();
        jugador.comprarCartaDesarrollo(carta, turnoActual);
    }

    public void usarCartaDesarrollo(CartasDesarrollo carta, Jugador jugador, ContextoCartaDesarrollo contexto, ProveedorDeDatos proveedor) {
        jugador.usarCartaDesarrollo(carta, contexto, proveedor);
    }
}