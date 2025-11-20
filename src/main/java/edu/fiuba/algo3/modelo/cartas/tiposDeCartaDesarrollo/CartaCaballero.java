package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Ladron;

public class CartaCaballero extends CartasDesarrollo{
  
    public CartaCaballero(Jugador jugador, int turnoDeCompra){
        super(jugador, turnoDeCompra);
    }

    public void usar(Jugador jugador, int turnoActual){
        if (!esJugable(turnoActual)){
            throw new IllegalStateException("No se puede usar esta carta todavia");
        }
        jugador.usarLadron();
    }


}