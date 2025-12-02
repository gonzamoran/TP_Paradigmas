package edu.fiuba.algo3.modelo.tiposBanca;

import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;

import java.util.ArrayList;

import edu.fiuba.algo3.modelo.excepciones.ComercioInvalidoException;


public class Banca2a1 extends Banca {
    private final Recurso recurso;
    public Banca2a1(Recurso recurso){
        this.recurso = recurso;
    }

    public void comerciar (Jugador jugador,  ArrayList<Recurso> oferta, Recurso demanda){
        int cantidadOfrecida = 0;
        for(Recurso recursoOfrecido : oferta){
            if(recursoOfrecido.getClass() != recurso.getClass()){
                throw new ComercioInvalidoException();
            } else {
                cantidadOfrecida += recursoOfrecido.obtenerCantidad();
            }
        }

        if (cantidadOfrecida != 2){
            throw new ComercioInvalidoException();
        }

        if (!jugador.poseeRecursosParaIntercambiar(oferta)){
            throw new ComercioInvalidoException();
        }

        for (Recurso recursoOfrecido : oferta){
            jugador.removerRecurso(recursoOfrecido);
        }
        jugador.agregarRecurso(demanda.obtenerCopia(1));
    }
}
