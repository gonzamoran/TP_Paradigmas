package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.cartas.*;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;

public class MazoTrucado extends MazoCartasDesarrollo {
    private CartasDesarrollo cartaTrucada;

    public MazoTrucado(CartasDesarrollo cartaTrucada) {
        super(new java.util.ArrayList<>(java.util.List.of(cartaTrucada)));
    }
}
