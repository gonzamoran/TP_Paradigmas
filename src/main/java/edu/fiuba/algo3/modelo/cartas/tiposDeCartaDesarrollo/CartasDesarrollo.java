package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;

public abstract class CartasDesarrollo {

    protected int turnoDeCompra;
    protected boolean fueUsada;

    public CartasDesarrollo() {
        int turnoDeCompra = 1;
    }

    public CartasDesarrollo(int turnoDeCompra) {
        this.turnoDeCompra = turnoDeCompra;
        this.fueUsada = false;
    }

    public int conseguirPV() {
        return 0;
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
    
    public abstract boolean esJugable(ContextoCartaDesarrollo contexto);

    public abstract void usar(ContextoCartaDesarrollo contexto, ProveedorDeDatos proveedor);

    public abstract CartasDesarrollo comprarCarta(int turnoActual);
}
