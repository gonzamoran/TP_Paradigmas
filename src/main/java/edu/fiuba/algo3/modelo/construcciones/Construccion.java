package edu.fiuba.algo3.modelo.construcciones;


import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.excepciones.PosInvalidaParaConstruirException;
import edu.fiuba.algo3.modelo.tablero.Vertice;
import java.util.List;


public abstract class Construccion {
    int puntosVictoria;
    private Jugador dueño;
    
    // renombramos el constructor?
    static public Construccion crearConstruccion(String tipo,Jugador jugActual, Vertice vertice){
        Construccion construccion = null;
        switch (tipo){
            case "Poblado":
                construccion = new Poblado();
                break;
            case "Ciudad":
                construccion = new Ciudad();
                break;
            case "Camino":
                //construccion = new Camino();
                break;
            default:
                throw new IllegalArgumentException("Tipo de construcción inválido"); //hacer excepcion personalizada
        }
        construccion.puntosVictoria = construccion.obtenerPuntosDeVictoria();
        construccion.dueño = jugActual;
        if (construccion.puedeConstruirse(jugActual, vertice)) {
            return construccion;
        }
        throw new PosInvalidaParaConstruirException();
    }

    public abstract int obtenerPuntosDeVictoria();

    public abstract boolean puedeConstruirse(Jugador jugador, Vertice vertice);

    public abstract boolean esPoblado();
}


/* 
 * NUEVA ESTRUCTURA DE CONSTRUCCION:
 * CONSTRUCCION ES ABSTRACTA
 * PARA CONSTRUIR: TIENE UN METODO PUEDECONSTRUIRSE() QUE RECIBE JUGADOR, VERTICE Y ADYACENTES (PENSANDO EN REGLA DE DISTANCIA)
 * DEVUELVE LA INSTANCIA DE LA CONSTRUCCION CORRESPONDIENTE (POBLADO, CIUDAD, CAMINO)
 * CADA SUBCLASE DE CONSTRUCCION SABE CUANTOS PUNTOS DE VICTORIA DA
 * CADA SUBCLASE DE CONSTRUCCION SABE QUE REQUISITOS TIENE PARA CONSTRUIRSE
 */