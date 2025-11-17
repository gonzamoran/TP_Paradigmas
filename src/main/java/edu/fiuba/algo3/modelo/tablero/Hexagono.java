package edu.fiuba.algo3.modelo.tablero;

import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.Recurso;

import edu.fiuba.algo3.modelo.excepciones.*;
import java.util.List;
import java.util.ArrayList;

public abstract class Hexagono {
    private boolean tieneLadron;

    public Hexagono() {
        this.tieneLadron = false;
    }

    public boolean esDesierto() {
        return false;
    }

    public void colocarLadron() {
        this.tieneLadron = true;
    }

    public void sacarLadron() {
        this.tieneLadron = false;
    }

    public boolean puedeGenerarRecursos(){
        if (tieneLadron) {
            return false;
        }
        return true;
    }

    public abstract Recurso generarRecurso(int cantidad);

    // public void agregarVerticeAdyacente(Vertice vertice) {
    //     if (!this.verticesAdyacentes.contains(vertice)) {
    //         this.verticesAdyacentes.add(vertice);
    //     }
    // }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Hexagono hexagono = (Hexagono) obj;
        return tieneLadron == hexagono.tieneLadron; //para este punto son dos hexagonos iguales
    }
}

/*
 * NUEVA ESTRUCTURA DE HEXAGONO:
 * YA NO CONOCE SUS COORDENADAS
 * EL CALCULO DE ARISTAS SE HACE EN TABLERO
 * SOLO SABE SI TIENE LADRON, QUE RECURSO ES Y QUE NUMERO TIENE
 * ES LLAMADO SIEMPRE POR VERTICE (O TABLERO TAL VEZ)
 */