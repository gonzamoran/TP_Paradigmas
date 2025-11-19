package edu.fiuba.algo3.modelo.tablero;

import java.util.List;

import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.excepciones.PosInvalidaParaConstruirException;
import java.util.ArrayList;

public class Vertice {
    private String dueño;
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

    public void construir(Construccion construccion, String jugador){
        this.construccion = construccion;
        this.dueño = jugador;
        this.estaConstruido = true;
    }

    public void agregarHexagono(Hexagono hexagono) {
        if (!hexagonosAdyacentes.contains(hexagono)) {
            hexagonosAdyacentes.add(hexagono);
        }
    }
    
    public ArrayList<Hexagono> obtenerHexagonosPorProduccion(Produccion produccion){
        ArrayList<Hexagono> res = new ArrayList<>();
        for (Hexagono hexagono : hexagonosAdyacentes) {
            if (hexagono.esDesierto()) {
                continue;
            }
            if (hexagono.compararProduccion(produccion)) {
                res.add(hexagono);
            }
        }
        return res;
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
    public boolean cumpleReglaDistancia() {
            for (Vertice vertice : verticesAdyacentes) {
                if (vertice.tieneConstruccion()) {
                    return false;
                }
            }
            return true;
        }
        
    public ArrayList<Recurso> construirInicial(Construccion construccion, String jugador) {
        for (Vertice vertice : verticesAdyacentes) {
            if (vertice.tieneConstruccion()) {
                throw new PosInvalidaParaConstruirException();
            }
        }
        this.construccion = construccion;
        this.dueño = jugador;
        this.estaConstruido = true;
        var recursos = new ArrayList<Recurso>();
        for (Hexagono hexagono : hexagonosAdyacentes) {
            int cantidad = this.construccion.obtenerPuntosDeVictoria();
            recursos.add(hexagono.generarRecurso(cantidad));
        }
        return recursos;
    }
    
    public ArrayList<Recurso> producirRecursos(int resultado){
        ArrayList<Recurso> recursosDelVertice = new ArrayList<>();
    
        if (!this.tieneConstruccion()){
            return new ArrayList<Recurso>();
        }
        ArrayList<Hexagono> hexagonos = this.obtenerHexagonosPorProduccion(new Produccion(resultado));
        
        for (Hexagono hexa: hexagonos){
            int cantidad = this.construccion.obtenerPuntosDeVictoria();
            recursosDelVertice.add(hexa.generarRecurso(cantidad));
        }
        return recursosDelVertice;
    }

    public boolean esDueno(String jugador){
        if(!tieneConstruccion()) return false;
        return this.construccion.esDueno(jugador);
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
