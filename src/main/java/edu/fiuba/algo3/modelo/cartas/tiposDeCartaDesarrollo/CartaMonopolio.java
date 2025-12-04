package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;
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

    @Override
    public void usar(ContextoCartaDesarrollo contexto, ProveedorDeDatos proveedor) {
        Recurso recursoElegido = proveedor.pedirRecursoAlUsuario();
        Jugador jugadorActual = contexto.conseguirJugadorQueUsaLaCarta();

        for (Jugador jugador : contexto.conseguirJugadoresAfectados()) {
            if (!jugador.equals(jugadorActual)) {
                Recurso recursoMonopolizado = jugador.vaciarRecurso(recursoElegido);

                jugadorActual.agregarRecurso(recursoMonopolizado);
            }
        }
        this.fueUsada = true;
    }

    public CartasDesarrollo comprarCarta(int turnoActual) {
        return new CartaMonopolio(turnoActual);
    }

}
