package edu.fiuba.algo3.modelo.tablero;

import java.util.*;

import edu.fiuba.algo3.modelo.tablero.Hexagono;

public class Coordenadas {
    private final int x;
    private final int y;

    /// Constructor de Coordenadass
    public Coordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /// Devuelve la coordenada X
    public int obtenerCoordenadaX() {
        return x;
    }

    /// Devuelve la coordenada y
    public int obtenerCoordenadaY() {
        return y;
    }
    /// Metodo para comparar 2 coordenadas.
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Coordenadas that = (Coordenadas) obj;
        return x == that.x && y == that.y;
    }

    public int hashCode() {
        return 31 * x + y;
    }

    /// No se usa.
    /*
    public List<Coordenadas> getVecinas(){
        return Arrays.asList(
                new Coordenadas(x + 1, y),      // derecha
                new Coordenadas(x - 1, y),      // izquierda
                new Coordenadas(x, y + 1),      // abajo
                new Coordenadas(x, y - 1),      // arriba
                new Coordenadas(x + 1, y - 1),  // arriba derecha
                new Coordenadas(x - 1, y + 1)   // abajo izquierda
        );
    }

     */
}