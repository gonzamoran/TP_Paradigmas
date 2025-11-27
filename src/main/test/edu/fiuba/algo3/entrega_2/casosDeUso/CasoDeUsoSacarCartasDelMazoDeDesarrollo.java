package edu.fiuba.algo3.entrega_2.casosDeUso;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;
import edu.fiuba.algo3.modelo.cartas.CartasJugador;
import edu.fiuba.algo3.modelo.cartas.MazoCartasDesarrollo;

import java.util.ArrayList;

public class CasoDeUsoSacarCartasDelMazoDeDesarrollo {
    private Jugador jugador;
    private ArrayList<CartasDesarrollo> cartas;

    // hacer constructores e implementaciones segun los tests

    public CasoDeUsoSacarCartasDelMazoDeDesarrollo(ArrayList<CartasDesarrollo> cartas) {
        this.cartas = cartas;
    }

    public CasoDeUsoSacarCartasDelMazoDeDesarrollo(Jugador jugador) {
        this.jugador = jugador;
    }

    public MazoCartasDesarrollo inicializarMazoDeCartasDeDesarrollo() {
        return new MazoCartasDesarrollo(cartas);
    }
    // implementar despues

    // MazoCartasDesarrollo va en modelo/cartas

    public void comprarCartaDesarrollo(MazoCartasDesarrollo mazo) {
        var carta = mazo.sacarCarta();
        jugador.comprarCartaDesarrollo(carta);
    }
    // DIEGO: implementar el check de las cartas de PV
}