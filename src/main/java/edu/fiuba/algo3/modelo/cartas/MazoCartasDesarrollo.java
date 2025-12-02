package edu.fiuba.algo3.modelo.cartas;

import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;
import java.util.ArrayList;
import java.util.List;

import edu.fiuba.algo3.modelo.excepciones.MazoVacioException;

public class MazoCartasDesarrollo {
    private final ArrayList<CartasDesarrollo> cartas;
    private int indiceCartaActual = 0;

    public MazoCartasDesarrollo(ArrayList<CartasDesarrollo> cartas) {
        this.cartas = cartas;
    }

    public CartasDesarrollo sacarCarta() {
        if (this.estaVacio()) {
            throw new MazoVacioException();
        }
        CartasDesarrollo cartaSacada = cartas.get(indiceCartaActual);
        indiceCartaActual++;
        return cartaSacada;
    }

    public boolean estaVacio() {
        return indiceCartaActual >= cartas.size();
    }

    public boolean equals(Object otroMazo) {
        if (otroMazo == null) {
            return false;
        }
        if (this.getClass() != otroMazo.getClass()) {
            return false;
        }
        MazoCartasDesarrollo mazoComparado = (MazoCartasDesarrollo) otroMazo;
        return this.cartas.equals(mazoComparado.cartas);
    }
}