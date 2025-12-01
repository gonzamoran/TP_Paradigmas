package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;

import edu.fiuba.algo3.modelo.tablero.Tablero;

import java.util.ArrayList;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Ladron;
import edu.fiuba.algo3.modelo.Recurso;


//  El jugador nombra un recurso. Todos los demás
//  jugadores deben entregarle todas las cartas de ese
//  tipo de recurso que posean.
 
public class CartaMonopolio extends CartasDesarrollo {

    public CartaMonopolio() {
        super();
    }

    public CartaMonopolio(int turnoDeCompra) {
        super(turnoDeCompra);
    }

    public void usar(ContextoCartaDesarrollo contexto) {
        this.fueUsada = true;
        Recurso recursoSolicitado = contexto.obtenerRecursoSolicitado();
        for (Jugador jugador : contexto.conseguirJugadoresAfectados()) {
            int cantidad = jugador.obtenerCantidadRecurso(recursoSolicitado);
            Recurso recursoEntregado = jugador.vaciarRecurso(recursoSolicitado);
            contexto.conseguirJugadorQueUsaLaCarta().agregarRecurso(recursoEntregado);
        }

    }

    public boolean esJugable(ContextoCartaDesarrollo contexto) {
        return contexto.sePuedeJugarCarta(this.turnoDeCompra);
    }

    public CartasDesarrollo comprarCarta(int turnoActual) {
        return new CartaMonopolio(turnoActual);
    }

}
