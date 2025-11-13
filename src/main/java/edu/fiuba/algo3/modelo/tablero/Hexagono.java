package edu.fiuba.algo3.modelo.tablero;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.excepciones.*;
import java.util.List;
import java.util.ArrayList;

public class Hexagono {
    private TipoRecurso recurso;
    private int id;
    private int numero;
    private ArrayList<Vertice> verticesAdyacentes;
    private boolean tieneLadron;

    public Hexagono(int id, TipoRecurso recurso) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID del hexagono no puede ser negativo");
        }
        this.id = id;
        this.recurso = recurso;
        this.tieneLadron = false;
        this.verticesAdyacentes = new ArrayList<>();
    }

    public void asignarNumero(int numero) {
        this.numero = numero;
    }

    public boolean esDesierto() {
        return this.recurso == TipoRecurso.DESIERTO;
    }

    public void colocarLadron() {
        this.tieneLadron = true;
    }

    public void sacarLadron() {
        this.tieneLadron = false;
    }

    public int obtenerNumeroFicha() {
        if (this.recurso == TipoRecurso.DESIERTO) {
            throw new DesiertoNoTieneFichaException();
        }
        return this.numero;
    }

    public TipoRecurso obtenerRecurso() {
        return recurso;
    }

    public boolean puedeGenerarRecursos() {
        if (this.tieneLadron) {
            return false;
        }
        if (this.esDesierto()) {
            return false;
        }
        return true;
    }

    public void agregarVerticeAdyacente(Vertice vertice) {
        if (!this.verticesAdyacentes.contains(vertice)) {
            this.verticesAdyacentes.add(vertice);
        }
    }
}

/*
 * NUEVA ESTRUCTURA DE HEXAGONO:
 * YA NO CONOCE SUS COORDENADAS
 * EL CALCULO DE ARISTAS SE HACE EN TABLERO
 * SOLO SABE SI TIENE LADRON, QUE RECURSO ES Y QUE NUMERO TIENE
 * ES LLAMADO SIEMPRE POR VERTICE (O TABLERO TAL VEZ)
 */