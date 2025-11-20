package edu.fiuba.algo3.entrega_2.casosDeUso;


import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;
import edu.fiuba.algo3.modelo.cartas.CartasJugador;

public class CasoDeUsoCartasDeDesarrollo{
    private Jugador jugador;
    private CartasJugador cartas;

    public CartasDesarrollo crearCartaAleatoria(Jugador jugador, int turnoActual){
        return new CartaPuntoVictoria(jugador, turnoActual);
    }  

    public boolean puedeComprar(Jugador jugador, int turnoActual){
        return cartas.poseeRecursosParaCartaDesarrollo();
    }
    public CartasDesarrollo comprarCartaDesarrollo(Jugador jugador, int turnoActual){
        if(!cartas.poseeRecursosParaCartaDesarrollo()){
            throw new IllegalStateException("No tiene recursos");
        }  
        cartas.pagarCartaDesarrollo();
        CartasDesarrollo carta = crearCartaAleatoria(this.jugador,turnoActual);
        cartas.agregarCartaDesarrollo(carta);

        return carta;
    } 
}