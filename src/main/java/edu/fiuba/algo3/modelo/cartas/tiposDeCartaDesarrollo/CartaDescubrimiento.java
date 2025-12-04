package edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.excepciones.NoSePuedeJugarEstaCartaException;

import java.util.ArrayList;

/*
 * Permite al jugador tomar 2 recursos a su elección
 * de la banca.
 */
public class CartaDescubrimiento extends CartasDesarrollo {
    public CartaDescubrimiento() {
        super();
    }

    public CartaDescubrimiento(int turnoDeCompra) {
        super(turnoDeCompra);
    }

    public CartasDesarrollo comprarCarta(int turnoActual) {
        return new CartaDescubrimiento(turnoActual);
    }

    @Override
    public void usar(ContextoCartaDesarrollo contexto, ProveedorDeDatos proveedor) {
        this.fueUsada = true;

        if (!contexto.sePuedeJugarCarta(this.turnoDeCompra)) {
            throw new NoSePuedeJugarEstaCartaException();
        }

        ArrayList<Recurso> recursosElegidos = proveedor.pedirGrupoRecursosAlUsuario(2);
        Jugador jugador = contexto.conseguirJugadorQueUsaLaCarta();

        for (Recurso recurso : recursosElegidos) {
            jugador.agregarRecurso(recurso);
        }
    }
}
