package edu.fiuba.algo3.modelo.construcciones;


import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.excepciones.*;
import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Recurso;
import java.util.List;


public abstract class Construccion {
    protected int puntosVictoria;
    protected Jugador jugador;

    public int obtenerPuntosDeVictoria() {
        return this.puntosVictoria;
    }

    public void asignarJugador(Jugador jugador){
        this.jugador = jugador;
        jugador.agregarConstruccion(this);
    }

    public abstract boolean esPoblado();

    public abstract boolean esCiudad();

    public abstract boolean puedeConstruirse(Construccion construccionPrevia);

    public boolean esDueno(Jugador jugador){
        return this.jugador.equals(jugador);
    }

    public abstract List<Recurso> obtenerRecursosNecesarios();
    
}


/* 
 * NUEVA ESTRUCTURA DE CONSTRUCCION:
 * CONSTRUCCION ES ABSTRACTA
 * PARA CONSTRUIR: TIENE UN METODO PUEDECONSTRUIRSE() QUE RECIBE JUGADOR, VERTICE Y ADYACENTES (PENSANDO EN REGLA DE DISTANCIA)
 * DEVUELVE LA INSTANCIA DE LA CONSTRUCCION CORRESPONDIENTE (POBLADO, CIUDAD, CAMINO)
 * CADA SUBCLASE DE CONSTRUCCION SABE CUANTOS PUNTOS DE VICTORIA DA
 * CADA SUBCLASE DE CONSTRUCCION SABE QUE REQUISITOS TIENE PARA CONSTRUIRSE
 */