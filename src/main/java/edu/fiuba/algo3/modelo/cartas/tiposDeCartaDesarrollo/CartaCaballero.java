package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.ContextoCartaDesarrollo;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;

/*
 * Mover el Ladrón y robar un recurso de un jugador
 * adyacente (similar a un 7, pero sin descarte de
 * cartas). Contribuyen a la Gran Caballería.
 * 
 */
public class CartaCaballero extends CartasDesarrollo {
        private Coordenadas coordDestino;
        private Jugador victima;

        public CartaCaballero(){
            super();
        }

        public CartaCaballero(int turnoDeCompra){
            super(turnoDeCompra);
        }

        public void configuracionCaballero(Coordenadas coordenadas){
            this.coordDestino = coordenadas;
        }

        public void usar(ContextoCartaDesarrollo contexto){
            this.fueUsada = true;
            // esto llama a moverLadronA(destino)
            contexto.moverLadron(this.coordDestino);
            contexto.robarA();
            Jugador jugador = contexto.conseguirJugadorQueUsaLaCarta();
            jugador.sumarCaballero(); 
        }

        public boolean esJugable(ContextoCartaDesarrollo contexto) {
            return contexto.sePuedeJugarCarta(this.turnoDeCompra);
        }

        public CartasDesarrollo comprarCarta(int turnoActual) {
            return new CartaCaballero(turnoActual);
        }

}
 