package edu.fiuba.algo3.modelo;

import java.util.ArrayList;

public abstract class Banca {
    public abstract void comerciar(Jugador jugador, ArrayList<Recurso> oferta, Recurso demanda);
}