package edu.fiuba.algo3.modelo.acciones;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;
import edu.fiuba.algo3.modelo.cartas.CartasJugador;
import edu.fiuba.algo3.modelo.cartas.MazoCartasDesarrollo;
import edu.fiuba.algo3.modelo.tiposRecurso.*;
import edu.fiuba.algo3.modelo.Recurso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CasoDeUsoSacarCartasDelMazoDeDesarrollo {
    private ArrayList<CartasDesarrollo> cartas;

    // hacer constructores e implementaciones segun los tests

    public CasoDeUsoSacarCartasDelMazoDeDesarrollo(ArrayList<CartasDesarrollo> cartas) {
        this.cartas = cartas;
    }

    public MazoCartasDesarrollo inicializarMazoDeCartasDeDesarrollo() {
        return new MazoCartasDesarrollo(cartas);
    }
    // implementar despues

    // MazoCartasDesarrollo va en modelo/cartas
    public void comprarCartaDesarrollo(MazoCartasDesarrollo mazo, Jugador jugador, int turnoActual) {
        var carta = mazo.sacarCarta();
        jugador.comprarCartaDesarrollo(carta, turnoActual);
    }

    public void usarCartaDesarrollo(CartasDesarrollo carta, Jugador jugador, ContextoCartaDesarrollo contexto) {
        jugador.usarCartaDesarrollo(carta, contexto);
    }
    // DIEGO: implementar el check de las cartas de PV
}