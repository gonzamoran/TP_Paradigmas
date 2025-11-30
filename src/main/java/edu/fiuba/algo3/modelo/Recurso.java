package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.excepciones.SumaDeRecursosDistintosException;
import edu.fiuba.algo3.modelo.tiposRecurso.Madera;
import edu.fiuba.algo3.modelo.tiposRecurso.Piedra;
import edu.fiuba.algo3.modelo.tiposRecurso.Lana;
import edu.fiuba.algo3.modelo.tiposRecurso.Grano;
import edu.fiuba.algo3.modelo.tiposRecurso.Ladrillo;
import edu.fiuba.algo3.modelo.tiposRecurso.Nulo;
import edu.fiuba.algo3.modelo.tiposRecurso.*;

public abstract class Recurso {

    protected int cantidad;

    public int getCantidad() {
        return cantidad;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Recurso recurso = (Recurso) obj;
        return this.cantidad == recurso.cantidad;
    }

    public void sumar(Recurso recurso) {
        if (this.getClass() != recurso.getClass()) {
            return;
        }
        this.cantidad += recurso.cantidad;
    }

    public abstract Recurso obtenerCopia(int cantidad);

    public int hashCode() {
        return getClass().hashCode();
    }

    public void restar(int cantidad) {
        this.cantidad -= cantidad;
    }

    public int obtenerCantidad() {
        return cantidad;
    }

}