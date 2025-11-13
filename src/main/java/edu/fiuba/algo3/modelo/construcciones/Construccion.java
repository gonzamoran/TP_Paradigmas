package edu.fiuba.algo3.modelo.construcciones;


import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.tablero.Vertice;
import java.util.List;


public abstract class Construccion {
    int puntosVictoria;
    private Jugador dueño;
    
    // renombramos el constructor?
    static public Construccion crearConstruccion(String tipo,Jugador jugActual){
        Construccion construccion = null;
        switch (tipo){
            case "Aldea":
                construccion = new Poblado();
                break;
            case "Ciudad":
                construccion = new Ciudad();
                break;
            case "Camino":
                construccion = new Camino();
                break;
            default:
                throw new IllegalArgumentException("Tipo de construcción inválido"); //hacer excepcion personalizada
        }
        construccion.puntosVictoria = construccion.obtenerPuntosDeVictoria();
        construccion.dueño = jugActual;

        return construccion;
    }
    
    public abstract int obtenerPuntosDeVictoria();

    public abstract boolean puedeConstruirse(Jugador jugador, Vertice vertice, List<Vertice> adyacentes, List<Construccion> construccionesAdyacentes);

    public abstract boolean esPoblado();

    public abstract boolean esCiudad();
}


/* 
 * NUEVA ESTRUCTURA DE CONSTRUCCION:
 * CONSTRUCCION ES ABSTRACTA
 * PARA CONSTRUIR: TIENE UN METODO PUEDECONSTRUIRSE() QUE RECIBE JUGADOR, VERTICE Y ADYACENTES (PENSANDO EN REGLA DE DISTANCIA)
 * DEVUELVE LA INSTANCIA DE LA CONSTRUCCION CORRESPONDIENTE (POBLADO, CIUDAD, CAMINO)
 * CADA SUBCLASE DE CONSTRUCCION SABE CUANTOS PUNTOS DE VICTORIA DA
 * CADA SUBCLASE DE CONSTRUCCION SABE QUE REQUISITOS TIENE PARA CONSTRUIRSE
 */