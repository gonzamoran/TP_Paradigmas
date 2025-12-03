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

    /*
    * Constructores de recurso. Si no recibe nada por parametro se setea a 0.
    */
    public Recurso() {
        this.cantidad = 0;
    }

    public Recurso(int cantidad) {
        this.cantidad = cantidad;
    }

    ///  Devuelve la cantidad de un recurso.
    public int obtenerCantidad() {
        return cantidad;
    }

    ///  Devuelve una copia del recurso.
    public abstract Recurso obtenerCopia(int cantidad);

    /// Suma una cantidad.
    public void sumar(Recurso recurso) {
        if (this.getClass() != recurso.getClass()) {
            return;
        }
        this.cantidad += recurso.cantidad;
    }
    ///  Resta una cantidad que recibe por parametro.
    public void restar(int cantidad) {
        this.cantidad -= cantidad;
    }


    /// Metodo para comparar 2 coordenadas.
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Recurso recurso = (Recurso) obj;
        return this.cantidad == recurso.cantidad;
    }

    public int hashCode() {
        return getClass().hashCode();
    }

}