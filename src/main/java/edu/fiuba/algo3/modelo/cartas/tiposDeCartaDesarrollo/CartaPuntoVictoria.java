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

    @Override
    public void usar(Jugador jugador, int turnoActual) {
        if (!this.esJugable()) {
            throw new IllegalStateException("No se puede usar esta carta todavia");
        }
        this.fueUsada = true;
        jugador.sumarPuntoVictoria();
    }
}