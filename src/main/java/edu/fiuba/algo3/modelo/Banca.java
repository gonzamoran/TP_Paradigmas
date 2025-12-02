package edu.fiuba.algo3.modelo;

import java.util.ArrayList;

public abstract class Banca {
    public boolean equals(Object otraBanca) {
        if (otraBanca == null) {
            return false;
        }
        if (this.getClass() != otraBanca.getClass()) {
            return false;
        }
        return true;
    }
    
    public abstract void comerciar(Jugador jugador, ArrayList<Recurso> oferta, Recurso demanda);

}