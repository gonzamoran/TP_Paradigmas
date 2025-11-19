package edu.fiuba.algo3.modelo.tablero;

import java.util.*;

import edu.fiuba.algo3.modelo.tablero.Hexagono;

public class Coordenadas {
    private final int x;
    private final int y;

    public Coordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }

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
    
    public int obtenerCoordenadaX() {
        return x;
    }

    public int obtenerCoordenadaY() {
        return y;
    }

    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Coordenadas that = (Coordenadas) obj;
        return x == that.x && y == that.y;
    }

    public int hashCode() {
        return 31 * x + y;
        // return Objects.hash(x, y);
    }
    
}