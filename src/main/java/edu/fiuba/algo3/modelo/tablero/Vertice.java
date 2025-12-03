package edu.fiuba.algo3.modelo.tablero;

import java.util.List;

import edu.fiuba.algo3.modelo.construcciones.Carretera;
import edu.fiuba.algo3.modelo.construcciones.Construccion;
import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.excepciones.*;
import java.util.ArrayList;

public class Vertice {
    private Jugador dueno;
    private List<Hexagono> hexagonosAdyacentes;
    private Construccion construccion;
    private boolean estaConstruido;
    private List<Vertice> verticesAdyacentes;
    private List<Construccion> carreterasIngresantes;
    private List<Banca> bancasDisponibles;


    public Vertice() {
        this.hexagonosAdyacentes = new ArrayList<>();
        this.verticesAdyacentes = new ArrayList<>();
        this.carreterasIngresantes = new ArrayList<>();
        this.bancasDisponibles = new ArrayList<>();
        this.estaConstruido = false;
    }


    //REVISAR
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
        if (construccion.equals(new Carretera())){
            if (this.carreterasIngresantes.size() > 0){
                return true;
            }
        }
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

    public boolean estaConstruidoCon(Construccion construccion, Jugador jugador) {

        if (this.tieneConstruccion() && this.construccion.equals(construccion) && this.esDueno(jugador)) {
            return true;
        }

        for (Construccion carretera : carreterasIngresantes) {
            if (carretera.equals(construccion) && carretera.esDueno(jugador)) {
                return true;
            }
        }
        
        return false;
    }

    public boolean esAdyacente(Vertice vertice) {
        return verticesAdyacentes.contains(vertice);
    }

    public void construirCarretera(Construccion carretera, Jugador jugador) {
        carretera.asignarJugador(jugador);
        //sacar recursos
        this.carreterasIngresantes.add(carretera);
    }

    public boolean poseeCarreterasDe(Jugador jugador) {
        return !carreterasIngresantes.isEmpty() && 
               carreterasIngresantes.stream().anyMatch(c -> c.esDueno(jugador));
    }

    public void agregarBanca(Banca banca) {
        this.bancasDisponibles.add(banca);
    }

    public List<Banca> obtenerBancasDisponibles() {
        return this.bancasDisponibles;
    }
    
        /*
        public List<Construccion> obtenerCarreterasVecinasDe(Jugador jugador, Construccion carreteraDeDondeVengo) {
            
            List<Construccion> vecinas = new ArrayList<>();
    
            if (this.construccion != null && !this.construccion.esDueno(jugador)) {
                return vecinas;
            }
    
            // Si no hay bloqueo, busco mis otras carreteras
            for (Construccion carretera : this.carreterasIngresantes) {
                if (carretera.esDueno(jugador) && !carretera.equals(carreteraDeDondeVengo)) {
                    vecinas.add(carretera);
                }
            }
    
            return vecinas;
        }*/
    
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
