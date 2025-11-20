package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.excepciones.SumaDeRecursosDistintosException;
import edu.fiuba.algo3.modelo.tiposRecurso.Madera;
import edu.fiuba.algo3.modelo.tiposRecurso.Piedra;
import edu.fiuba.algo3.modelo.tiposRecurso.Lana;
import edu.fiuba.algo3.modelo.tiposRecurso.Grano;
import edu.fiuba.algo3.modelo.tiposRecurso.Ladrillo;
import edu.fiuba.algo3.modelo.tiposRecurso.Nulo;

public abstract class Recurso {

    protected int cantidad;
    protected String tipo;

    static public Recurso generarRecurso(String tipo, int cantidad) {
        Recurso recurso = null;
        switch (tipo) {
            case "Madera":
                recurso = new Madera(tipo, cantidad);
                break;
            case "Piedra":
                recurso = new Piedra(tipo, cantidad);
                break;
            case "Lana":
                recurso = new Lana(tipo, cantidad);
                break;
            case "Grano":
                recurso = new Grano(tipo, cantidad);
                break;
            case "Ladrillo":
                recurso = new Ladrillo(tipo, cantidad);
                break;
            default:
                recurso = new Nulo();
        }
        return recurso;
    }

    public String getTipo() {
        return tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Recurso recurso = (Recurso) obj;
        return this.tipo == recurso.tipo &&
               this.cantidad == recurso.cantidad;
    }

    public void sumar(Recurso recurso) {
        if (this.tipo != recurso.tipo) {
            throw new SumaDeRecursosDistintosException();
        }
        ;
        this.cantidad += recurso.cantidad;
    }

    public int hashCode() {
        return tipo.hashCode();
    }

    public int obtenerCantidad() {
        return cantidad;
    }

}