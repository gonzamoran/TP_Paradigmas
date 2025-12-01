package edu.fiuba.algo3.modelo.tiposBanca;

import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import java.util.ArrayList;

public class Banca4a1 extends Banca {
    public void comerciar(Jugador jugador, ArrayList<Recurso> oferta, Recurso demanda) {
        int cantidadOfrecida = 0;
        Class<? extends Recurso> claseRecurso = null;

        for (edu.fiuba.algo3.modelo.Recurso recurso : oferta) {
            if (claseRecurso == null) {
                claseRecurso = recurso.getClass();
            } else if (!claseRecurso.equals(recurso.getClass())) {
                throw new IllegalArgumentException("Todos los recursos ofrecidos deben ser del mismo tipo para comerciar con la banca 4:1"); //cambiar excepcion
            }
            cantidadOfrecida += recurso.obtenerCantidad();
        }
        if (!jugador.poseeRecursosParaIntercambiar(oferta)) {
            throw new IllegalArgumentException("El jugador no posee los recursos ofrecidos para comerciar con la banca 4:1"); //cambiar excepcion
        }

        if (cantidadOfrecida != 4){
            throw new IllegalArgumentException("La cantidad ofrecida debe ser exactamente 4 para comerciar con la banca 4:1"); //cambiar excepcion
        }

        for (edu.fiuba.algo3.modelo.Recurso recurso : oferta) {
            jugador.removerRecurso(recurso);
        }
        jugador.agregarRecurso(demanda.obtenerCopia(1));
    }    
}
