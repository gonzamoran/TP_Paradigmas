package edu.fiuba.algo3.modelo.tiposBanca;

import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.excepciones.ComercioInvalidoException;

import java.util.ArrayList;

public class Banca4a1 extends Banca {
    public void comerciar(Jugador jugador, ArrayList<Recurso> oferta, ArrayList<Recurso> demanda) {
        int cantidadOfrecida = 0;
        Recurso modelo = oferta.get(0);

        for (edu.fiuba.algo3.modelo.Recurso recurso : oferta) {
            if (modelo != recurso) {
                throw new ComercioInvalidoException();
            }
            cantidadOfrecida += recurso.obtenerCantidad();
        }
        if (!jugador.poseeRecursos(oferta)) {
            throw new ComercioInvalidoException();
        }
        int cantidadDemandada = 0;
        for (Recurso recursoDemandado : demanda){
            cantidadDemandada += recursoDemandado.obtenerCantidad();
        }

        if (cantidadOfrecida != 4 * cantidadDemandada){
            throw new ComercioInvalidoException();
        }

        for (edu.fiuba.algo3.modelo.Recurso recurso : oferta) {
            jugador.removerRecurso(recurso);
        }
        for (Recurso recursoDemandado : demanda){
            jugador.agregarRecurso(recursoDemandado.obtenerCopia(recursoDemandado.obtenerCantidad()));
        }
    }    
}
