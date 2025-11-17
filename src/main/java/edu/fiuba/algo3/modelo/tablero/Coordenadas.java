package edu.fiuba.algo3.modelo.tablero;

import java.util.ArrayList;

import edu.fiuba.algo3.modelo.tablero.Hexagono;

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

    public boolean equals(Object obj) {

        Coordenadas that = (Coordenadas) obj;
        return x == that.x && y == that.y;
    }

    public int hashCode() {
        return 31 * x + y;
    }
    
}