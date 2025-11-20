package edu.fiuba.algo3.entrega_2.casosDeUso;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;

public class CasoDeUsoIntercambio {
    private Jugador jugador1;
    private Jugador jugador2;

    public CasoDeUsoIntercambio(Jugador jugador1, Jugador jugador2, Recurso[] recursosJugador1, Recurso[] recursosJugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        for (Recurso recurso : recursosJugador1) {
            jugador1.agregarRecurso(recurso);
        }
        for (Recurso recurso : recursosJugador2) {
            jugador2.agregarRecurso(recurso);
        }
    }

    public boolean puedeHacerseElIntercambio(ArrayList<Recurso> recursosAEntregarJugador1, ArrayList<Recurso> recursosARecibirJugador1) {
        if (!jugador1.poseeRecursosParaIntercambiar(recursosAEntregarJugador1) || !jugador2.poseeRecursosParaIntercambiar(recursosARecibirJugador1)) {
            return false;
        }
        return true;
    }

    public void ejecutarIntercambio(ArrayList<Recurso> recursosAEntregarJugador1, ArrayList<Recurso> recursosARecibirJugador1) {
        jugador1.intercambiar(recursosAEntregarJugador1, recursosARecibirJugador1, jugador2);
    }
}
