package edu.fiuba.algo3.modelo.cartas;

import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.*;
import java.util.ArrayList;
import java.util.List;

import edu.fiuba.algo3.modelo.excepciones.MazoVacioException;

public class MazoCartasDesarrollo {
    private final ArrayList<CartasDesarrollo> cartas;
    private int indiceCartaActual = 0;

    public MazoCartasDesarrollo() {
        this.cartas = new ArrayList<CartasDesarrollo>(List.of(
                new CartaPuntoVictoria(), new CartaPuntoVictoria(), new CartaPuntoVictoria(), new CartaPuntoVictoria(),
                new CartaPuntoVictoria()));
        // new CartaMonopolio(), new CartaMonopolio(),
        // new CartaConstruccionCarretera(), new CartaConstruccionCarretera(),
        // new CartaDescubrimiento(), new CartaDescubrimiento(),
        // new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new
        // CartaCaballero(),
        // new CartaCaballero(),
        // new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new
        // CartaCaballero(),
        // new CartaCaballero(),
        // new CartaCaballero(), new CartaCaballero(), new CartaCaballero(), new
        // CartaCaballero()));
    }

    public MazoCartasDesarrollo(ArrayList<CartasDesarrollo> cartasPersonalizadas) {
        this.cartas = cartasPersonalizadas;
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
}