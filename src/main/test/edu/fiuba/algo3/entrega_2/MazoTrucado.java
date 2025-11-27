package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.cartas.*;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;

public class MazoTrucado extends MazoCartasDesarrollo {
    private CartasDesarrollo cartaTrucada;

    public MazoTrucado(CartasDesarrollo cartaTrucada) {
        super();
        this.cartaTrucada = cartaTrucada;
    }

    public CartasDesarrollo obtenerCarta() {
        return this.cartaTrucada;
    }
}
