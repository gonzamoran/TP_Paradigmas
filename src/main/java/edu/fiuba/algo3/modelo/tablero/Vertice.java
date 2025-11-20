package edu.fiuba.algo3.modelo.tablero;

import java.util.List;

import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.excepciones.*;
import java.util.ArrayList;

public class Vertice {
    private Jugador dueno;
    private List<Hexagono> hexagonosAdyacentes;
    private Construccion construccion;
    private boolean estaConstruido;
    private List<Vertice> verticesAdyacentes;
    private List<List<Vertice>> caminosIngresantes;

    // deberia tener referencia al jugador dueño de la construccion?

    public Vertice() {
        this.hexagonosAdyacentes = new ArrayList<>();
        this.verticesAdyacentes = new ArrayList<>();
        this.caminosIngresantes = new ArrayList<>();
        this.estaConstruido = false;
    }

    public void construir(Construccion construccion, Jugador jugador) {
        var recursosNecesarios = construccion.obtenerRecursosNecesarios();
        if (!jugador.poseeRecursosParaConstruir(construccion)) {
            return;
        }
        for (Recurso recurso : recursosNecesarios) {
            jugador.removerRecurso(recurso);
        }
        if (dueno != null){
            dueno.removerConstruccion(this.construccion);
        }
        this.construccion = construccion;
        this.dueno = jugador;
        this.estaConstruido = true;
        construccion.asignarJugador(jugador);
    }

    public void agregarHexagono(Hexagono hexagono) {
            hexagonosAdyacentes.add(hexagono);
    }

    public Jugador obtenerDueno() {
        return this.dueno;
    }

    public ArrayList<Hexagono> obtenerHexagonosPorProduccion(Produccion produccion) {
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
        if (!estaConstruido) {
            return false;
        }
        return true;
    }

    public boolean puedeConstruirse(Construccion construccion) {
        if (!construccion.puedeConstruirse(this.construccion)) {
            return false;
        }
        return true;
    }


    public boolean cumpleReglaDistancia() {
        for (Vertice vertice : verticesAdyacentes) {
            if (vertice.tieneConstruccion()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Recurso> construirInicial(Construccion construccion, Jugador jugador) {
        for (Vertice vertice : verticesAdyacentes) {
            if (vertice.tieneConstruccion()) {
                throw new PosInvalidaParaConstruirException();
            }
        }
        this.construccion = construccion;
        this.dueno = jugador;
        this.estaConstruido = true;
        construccion.asignarJugador(jugador);
        
        var recursos = new ArrayList<Recurso>();
        for (Hexagono hexagono : hexagonosAdyacentes) {
            int cantidad = this.construccion.obtenerPuntosDeVictoria();
            recursos.add(hexagono.generarRecurso(cantidad));
        }
        return recursos;
    }

    public ArrayList<Recurso> producirRecursos(int resultado) {
        ArrayList<Recurso> recursosDelVertice = new ArrayList<>();

        if (!this.tieneConstruccion()) {
            return new ArrayList<Recurso>();
        }
        ArrayList<Hexagono> hexagonos = this.obtenerHexagonosPorProduccion(new Produccion(resultado));

        for (Hexagono hexa : hexagonos) {
            int cantidad = this.construccion.obtenerPuntosDeVictoria();
            recursosDelVertice.add(hexa.generarRecurso(cantidad));
        }
        return recursosDelVertice;
    }

    public boolean esDueno(Jugador jugador) {
        if (!tieneConstruccion())
            return false;
        return this.construccion.esDueno(jugador);
    }

    public boolean esPoblado() {
        if (!tieneConstruccion())
            return false;
        return this.construccion.esPoblado();
    }

    public boolean esCiudad() {
        if (!tieneConstruccion())
            return false;
        return this.construccion.esCiudad();
    }

    public void mejorarACiudad(Coordenadas coordenadas, Jugador jugador) {
        if(!this.esDueno(jugador) || !this.esPoblado()) {
            throw new ErrorAlMejorarConstruccionException();
        }
        // implementar el colocar la ciudad, chequear recursos, etc
        
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
 * TIENE METODOS PARA COLOCAR CONSTRUCCION, CONSULTAR DUEÑO, CONSULTAR TIPO DE
 * CONSTRUCCION?
 * 
 * 
 */
