package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Ladron;

public class CartaPuntoVictoria extends CartasDesarrollo{
    
    public CartaPuntoVictoria(){
        super();
    }
    
    public CartaPuntoVictoria(Jugador jugador, int turnoDeCompra){
        super(jugador, turnoDeCompra);
    }

    @Override
    public void usar(Jugador jugador, int turnoActual){
        if (!esJugable(turnoActual)){
            throw new IllegalStateException("No se puede usar esta carta todavia");
        }
        this.fueUsada = true;
        jugador.sumarPuntoVictoria();
    }
}
