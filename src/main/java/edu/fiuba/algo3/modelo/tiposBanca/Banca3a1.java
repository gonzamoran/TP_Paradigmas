package edu.fiuba.algo3.modelo.tiposBanca;

import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import java.util.ArrayList;


public class Banca3a1 extends Banca {
    public void comerciar(Jugador jugador, ArrayList<Recurso> oferta, Recurso demanda) {
        int cantidadTotalOfrecida = 0;
        for (Recurso recurso : oferta) {
            cantidadTotalOfrecida += recurso.obtenerCantidad();
        }

        if (cantidadTotalOfrecida != 3) {
            throw new RuntimeException(); //cambiar excepcion
        }

        if (!jugador.poseeRecursosParaIntercambiar(oferta)) {
            throw new RuntimeException("El jugador no posee los recursos ofrecidos para comerciar con la banca 3:1"); //cambiar excepcion
        }

        for (Recurso recurso : oferta) {
            jugador.removerRecurso(recurso);
        }

        jugador.agregarRecurso(demanda.obtenerCopia(1));
    }
}
