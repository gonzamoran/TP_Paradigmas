package edu.fiuba.algo3.modelo.tiposBanca;

import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.excepciones.ComercioInvalidoException;

import java.util.ArrayList;


public class Banca2a1 extends Banca {
    private final Recurso recurso;
    public Banca2a1(Recurso recurso){
        this.recurso = recurso;
    }

    public Recurso obtenerRecursoTipo() {
        return recurso.obtenerCopia(0);
    }

    public void comerciar (Jugador jugador,  ArrayList<Recurso> oferta, ArrayList<Recurso> demanda){
        int cantidadOfrecida = 0;
        for(Recurso recursoOfrecido : oferta){
            if(recursoOfrecido.getClass() != recurso.getClass()){
                throw new ComercioInvalidoException();
            } else {
                cantidadOfrecida += recursoOfrecido.obtenerCantidad();
            }
        }
        
        int cantidadDemandada = 0;

        for (Recurso recursoDemandado : demanda){
            cantidadDemandada += recursoDemandado.obtenerCantidad();
        }

        if (cantidadOfrecida != cantidadDemandada * 2){
            throw new ComercioInvalidoException();
        }

        if (!jugador.poseeRecursos(oferta)){
            throw new ComercioInvalidoException();
        }

        for (Recurso recursoOfrecido : oferta){
            jugador.removerRecurso(recursoOfrecido);
        }

        for (Recurso recursoDemandado : demanda){
            jugador.agregarRecurso(recursoDemandado.obtenerCopia(recursoDemandado.obtenerCantidad()));
        }
    }
}
