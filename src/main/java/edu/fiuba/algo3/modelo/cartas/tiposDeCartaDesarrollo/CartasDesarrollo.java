package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tiposRecurso.*;

public abstract class CartasDesarrollo {

    protected int turnosRestantes;
    protected int turnoDeCompra;
    protected boolean fueUsada;
    protected Jugador jugador;

    public CartasDesarrollo() {
        this.fueUsada = false;
    }

    public CartasDesarrollo(int turnoActual) {
        this.turnoDeCompra = turnoActual;
        this.fueUsada = false;
    }

    /*
     * public boolean esJugable(int turnoActual) {
     * return !fueUsada && turnoActual > turnoDeCompra;
     * }
     */
    public boolean esJugable() {
        return turnosRestantes == 0;
    }

    public boolean equals(Object otraCarta) {
        if (otraCarta == null) {
            return false;
        }
        if (this.getClass() != otraCarta.getClass()) {
            return false;
        }
        return true;
    }

    public abstract void usar(Jugador jugador, int turnoActual);

}
