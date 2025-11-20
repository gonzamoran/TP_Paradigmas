package edu.fiuba.algo3.modelo.tablero;

import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.Recurso;

import edu.fiuba.algo3.modelo.excepciones.*;
import java.util.List;
import java.util.ArrayList;

public abstract class Hexagono {
    private boolean tieneLadron;
    private Produccion produccion;

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

    public boolean puedeGenerarRecursos() {
        return !this.tieneLadron;
    }

    public void asignarProduccion(Produccion produccion) {
        this.produccion = produccion;
    }

    public boolean compararProduccion(Produccion produccion) {
        if (this.produccion == null || produccion == null) {
            return false;
        }
        return this.produccion.equals(produccion);
    }

    public abstract Recurso generarRecurso(int cantidad);

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;
        Hexagono hexagono = (Hexagono) obj;
        return tieneLadron == hexagono.tieneLadron &&
        this.produccion.equals(hexagono.produccion);
    }
}

/*
 * NUEVA ESTRUCTURA DE HEXAGONO:
 * YA NO CONOCE SUS COORDENADAS
 * EL CALCULO DE ARISTAS SE HACE EN TABLERO
 * SOLO SABE SI TIENE LADRON, QUE RECURSO ES Y QUE NUMERO TIENE
 * ES LLAMADO SIEMPRE POR VERTICE (O TABLERO TAL VEZ)
 */