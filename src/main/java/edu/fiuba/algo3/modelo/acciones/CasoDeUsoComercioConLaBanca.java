package edu.fiuba.algo3.modelo.acciones;

import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.Recurso;

import java.util.ArrayList;
import java.util.List;

public class CasoDeUsoComercioConLaBanca {
    private Jugador jugador;
    private Tablero tablero;

    public CasoDeUsoComercioConLaBanca(Jugador jugador, Tablero tablero) {
        this.jugador = jugador;
        this.tablero = tablero;
    }

    public void comerciar(ArrayList<Recurso> oferta, ArrayList<Recurso> demanda, Banca banca) {
        banca.comerciar(jugador, oferta, demanda);
    }

    public ArrayList<Banca> obtenerBancasDisponibles() {
        return tablero.obtenerBancasDisponibles(this.jugador);
    }
}
