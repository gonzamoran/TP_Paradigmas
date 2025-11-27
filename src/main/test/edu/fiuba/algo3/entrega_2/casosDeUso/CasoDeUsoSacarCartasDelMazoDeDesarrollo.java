package edu.fiuba.algo3.entrega_2.casosDeUso;


import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;
import edu.fiuba.algo3.modelo.cartas.CartasJugador;


public class CasoDeUsoSacarCartasDelMazoDeDesarrollo{
    private Jugador jugador;
    private CartasJugador cartas;

    //hacer constructores e implementaciones segun los tests

    //implementar despues

    //MazoCartasDesarrollo va en modelo/cartas

    public void comprarCartaDesarrollo(){
        var carta = new CartaPuntoVictoria();
        jugador.agregarCartaDesarrollo(carta);
    }
    // DIEGO: implementar el check de las cartas de PV
}