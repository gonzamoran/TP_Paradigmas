package edu.fiuba.algo3.modelo.tiposBanca;

import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import java.util.ArrayList;

import edu.fiuba.algo3.modelo.excepciones.ComercioInvalidoException;

public class Banca4a1 extends Banca {
    public void comerciar(Jugador jugador, ArrayList<Recurso> oferta, Recurso demanda) {
        int cantidadOfrecida = 0;
        Class<? extends Recurso> claseRecurso = null;

        for (edu.fiuba.algo3.modelo.Recurso recurso : oferta) {
            if (claseRecurso == null) {
                claseRecurso = recurso.getClass();
            } else if (!claseRecurso.equals(recurso.getClass())) {
                throw new ComercioInvalidoException();
            }
            cantidadOfrecida += recurso.obtenerCantidad();
        }
        if (!jugador.poseeRecursosParaIntercambiar(oferta)) {
            throw new ComercioInvalidoException();
        }

        if (cantidadOfrecida != 4){
            throw new ComercioInvalidoException();
        }

        for (edu.fiuba.algo3.modelo.Recurso recurso : oferta) {
            jugador.removerRecurso(recurso);
        }
        jugador.agregarRecurso(demanda.obtenerCopia(1));
    }    
}
