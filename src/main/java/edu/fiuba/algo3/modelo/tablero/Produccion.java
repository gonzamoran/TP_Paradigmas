package edu.fiuba.algo3.modelo.tablero;

public class Produccion {
    private int numero;

    public Produccion(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Produccion produccion = (Produccion) obj;
        return numero == produccion.numero;
    }
}
