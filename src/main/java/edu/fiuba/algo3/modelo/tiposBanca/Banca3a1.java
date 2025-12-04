package edu.fiuba.algo3.modelo.tiposBanca;

import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import java.util.ArrayList;

import edu.fiuba.algo3.modelo.excepciones.ComercioInvalidoException;

public class Banca3a1 extends Banca {
    public void comerciar(Jugador jugador, ArrayList<Recurso> oferta, ArrayList<Recurso> demanda) {
        int cantidadTotalOfrecida = 0;
        for (Recurso recurso : oferta) {
            cantidadTotalOfrecida += recurso.obtenerCantidad();
        }

        int cantidadTotalDemandada = 0;
        for (Recurso recurso : demanda) {
            cantidadTotalDemandada += recurso.obtenerCantidad();
        }

        if (cantidadTotalOfrecida != 3 * cantidadTotalDemandada) {
            throw new ComercioInvalidoException();
        }

        if (!jugador.poseeRecursosParaIntercambiar(oferta)) {
            throw new ComercioInvalidoException();
        }

        for (Recurso recurso : oferta) {
            jugador.removerRecurso(recurso);
        }
        for (Recurso recursoDemandado : demanda){
            jugador.agregarRecurso(recursoDemandado.obtenerCopia(recursoDemandado.obtenerCantidad()));
        }
    }
}
