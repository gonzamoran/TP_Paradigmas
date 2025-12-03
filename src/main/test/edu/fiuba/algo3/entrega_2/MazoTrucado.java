package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.cartas.*;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;

import java.util.List;
import java.util.ArrayList;

public class MazoTrucado extends MazoCartasDesarrollo {
    private CartasDesarrollo cartaTrucada;

    public MazoTrucado(ArrayList<CartasDesarrollo> cartasTrucada) {
        super(cartasTrucada);
    }
}
