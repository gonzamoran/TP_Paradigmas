package edu.fiuba.algo3.modelo.acciones;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;

import java.util.ArrayList;

public class CasoDeUsoIntercambio {
    private Jugador jugador1;
    private Jugador jugador2;

    public CasoDeUsoIntercambio(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

    public void ejecutarIntercambio(ArrayList<Recurso> oferta, ArrayList<Recurso> demanda) {
        jugador1.intercambiar(oferta, demanda, jugador2);
    }
}
