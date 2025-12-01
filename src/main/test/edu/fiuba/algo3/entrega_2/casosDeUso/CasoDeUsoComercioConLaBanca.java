package edu.fiuba.algo3.entrega_2.casosDeUso;

import java.util.ArrayList;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.Recurso;

public class CasoDeUsoComercioConLaBanca {
    private Jugador jugador;
    private Banca banca;

    public CasoDeUsoComercioConLaBanca(Jugador jugador, Banca banca) {
        this.jugador = jugador;
        this.banca = banca;
    }

    public void comerciar(ArrayList<Recurso> oferta, Recurso demanda) {
        banca.comerciar(jugador, oferta, demanda);
    }
}
