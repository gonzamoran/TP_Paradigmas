package edu.fiuba.algo3.modelo.tablero;

import java.util.List;

import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.excepciones.PosInvalidaParaConstruirException;
import java.util.ArrayList;

public class Vertice {
    private Jugador dueño;
    private List<Hexagono> hexagonosAdyacentes;
    private Construccion construccion;
    private boolean estaConstruido;
    private List<Vertice> verticesAdyacentes;
    private List<List<Vertice>> caminosIngresantes;

    //deberia tener referencia al jugador dueño de la construccion?
    
    public Vertice() {
        this.hexagonosAdyacentes = new ArrayList<>();
        this.verticesAdyacentes = new ArrayList<>();
        this.caminosIngresantes = new ArrayList<>();
        this.estaConstruido = false;
    }

    public void agregarHexagono(Hexagono hexagono) {
        if (!hexagonosAdyacentes.contains(hexagono)) {
            hexagonosAdyacentes.add(hexagono);
        }
    }

    public void agregarAdyacente(Vertice unVertice) {
        if (!verticesAdyacentes.contains(unVertice)) {
            verticesAdyacentes.add(unVertice);
        }
    }

    public boolean tieneConstruccion() {
        if(!estaConstruido){
            return false;
        }
        return true;
    }

    public boolean esPoseidoPor(Jugador jugador) {
        return tieneConstruccion() && dueño.equals(jugador);
    }
    /*
    private List<Construccion> conseguirConstruccionesAdyacentes() {
        for (Vertice vertice : verticesAdyacentes) {
            if (vertice.estaConstruido()) {
                construccionesAdyacentes.add(vertice.construccion);
            }
        }
        return construccionesAdyacentes;
    }
    */
   
    public void construirEn(String tipoConstruccion, Jugador jugador) {
        for (Vertice vertice : verticesAdyacentes) {
            if (vertice.tieneConstruccion()) {
                throw new PosInvalidaParaConstruirException();
            }
        }
        this.construccion = Construccion.crearConstruccion(tipoConstruccion, jugador, this);
        this.estaConstruido = true;
    }

        /*
        Construccion nuevaConstruccion = Construccion.crearConstruccion(tipoConstruccion, jugador);

        List<Construccion> construccionesAdyacentes = conseguirConstruccionesAdyacentes();

        if (nuevaConstruccion.puedeConstruirse(jugador, this, verticesAdyacentes, construccionesAdyacentes)) {
            this.construccion = nuevaConstruccion;
            this.dueño = jugador;
            this.estaConstruido = true;
            return true;
        }
        
        return false;
        */
    
    public boolean esPoblado() {
        if (!estaConstruido) {
            return false;
        }
        return this.construccion.esPoblado();
    }

}


/*
 * NUEVA ESTRUCTURA DE VERTICE:
 * 
 * NO CONOCE SUS COORDENADAS
 * TIENE REFERENCIAS A QUE VERTICES SON ADYACENTES
 * EL CALCULO DE ADYACENTES SE HACE EN TABLERO
 * TIENE REFERENCIAS A QUE HEXAGONOS SON ADYACENTES
 * TIENE REFERENCIA A QUE CONSTRUCCION HAY EN EL VERTICE
 * TIENE REFERENCIA A QUE JUGADOR ES DUEÑO DE LA CONSTRUCCION
 * TIENE METODOS PARA COLOCAR CONSTRUCCION, CONSULTAR DUEÑO, CONSULTAR TIPO DE CONSTRUCCION?
 * 
 * 
 */
