package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.Hexagono;
import java.util.ArrayList;

public class Coordenadas {
    private final int x;
    private final int y;

    public Coordenadas(int coord_x, int coord_y) {
        this.x = coord_x;
        this.y = coord_y;
    }

    public int obtenerCoordenadaX() {
        return x;
    }

    public int obtenerCoordenadaY() {
        return y;
    }
}