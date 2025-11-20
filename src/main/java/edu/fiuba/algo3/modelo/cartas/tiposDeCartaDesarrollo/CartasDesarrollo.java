package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.Jugador;

public abstract class CartasDesarrollo {

    protected int  turnoDeCompra;
    protected boolean fueUsada;
    protected Jugador jugador;

    public CartasDesarrollo(Jugador jugador, int turnoActual){
        this.jugador = jugador;
        this.turnoDeCompra = turnoActual;
        this.fueUsada = false;
    }

    public boolean esJugable(int turnoActual){
        return !fueUsada && turnoActual > turnoDeCompra;
    }

    public abstract void usar(Jugador jugador, int turnoActual);
}