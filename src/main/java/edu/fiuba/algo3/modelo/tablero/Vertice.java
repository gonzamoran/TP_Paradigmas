package edu.fiuba.algo3.modelo.tablero;

import edu.fiuba.algo3.modelo.Banca;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.construcciones.Carretera;
import edu.fiuba.algo3.modelo.construcciones.Construccion;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private Jugador dueno;
    private List<Hexagono> hexagonosAdyacentes;
    private Construccion construccion;
    private boolean estaConstruido;
    private ArrayList<Vertice> verticesAdyacentes;
    private ArrayList<Carretera> carreterasIngresantes;
    private ArrayList<Banca> bancasDisponibles;


    public Vertice() {
        this.hexagonosAdyacentes = new ArrayList<>();
        this.verticesAdyacentes = new ArrayList<>();
        this.carreterasIngresantes = new ArrayList<>();
        this.bancasDisponibles = new ArrayList<>();
        this.estaConstruido = false;
    }

    
    public void construir(Construccion construccion, Jugador jugador) {
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

    public boolean cumpleReglaDistancia() {
        for (Vertice vertice : verticesAdyacentes) {
            if (vertice.tieneConstruccion()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Recurso> construirInicial(Construccion construccion, Jugador jugador) {
        this.construir(construccion, jugador);
        
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
        Carretera carreteraCast = (Carretera) carretera;
        carreteraCast.asignarJugador(jugador);
        carreteraCast.asignarVertice(this);
        this.carreterasIngresantes.add(carreteraCast);
    }

    public void agregarBanca(Banca banca) {
        this.bancasDisponibles.add(banca);
    }

    public ArrayList<Banca> obtenerBancasDisponibles() {
        return this.bancasDisponibles;
    }


    public boolean poseeCarreterasDe(Jugador jugador) {
        for (Carretera c : this.carreterasIngresantes) {
            if (c.esDueno(jugador)) return true;
        }
        return false;
    }

    public List<Vertice> verticesConCarreterasAdyacentes(Jugador jugador) {
        List<Vertice> list = new ArrayList<>();

        for (Vertice v : verticesAdyacentes) {
            if (!v.poseeCarreterasDe(jugador)) continue;

            for (Carretera carretera : carreterasIngresantes) {
                if (carretera.conecta(this, v) && carretera.esDueno(jugador)) {
                    list.add(v);
                    break;
                }
            }
        }
        return list;
    }

    public int gradoJugador(Jugador jugador) {
        return verticesConCarreterasAdyacentes(jugador).size();
    }

    public boolean esExtremoReal(Jugador jugador) {
        return gradoJugador(jugador) == 1;
    }
}
