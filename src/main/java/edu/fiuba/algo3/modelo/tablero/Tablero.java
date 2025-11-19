package edu.fiuba.algo3.modelo.tablero;

// import java.system.out;
import java.util.List;
import java.lang.reflect.Constructor;
import java.util.*;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.tablero.Ladron;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.construcciones.Construccion;

import edu.fiuba.algo3.modelo.tablero.tiposHexagono.Desierto;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.Bosque;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.Campo;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.Colina;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.Montana;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.Pastizal;


import edu.fiuba.algo3.modelo.excepciones.PosInvalidaParaConstruirException;

import java.util.HashMap;
import java.util.Collections;

public class Tablero {
    private Ladron ladron;
    private Map<Coordenadas, Carretera> carreteras;
    // private Map<Coordenadas, Poblado> poblados;
    private ArrayList<Hexagono> listaHexagonos;
    private ArrayList<Produccion> listaNumeros;
    private Map<Coordenadas, Hexagono> mapaHexagonos;
    private Map<Coordenadas, Vertice> mapaVertices;


    public Tablero() {
        this.ladron = new Ladron();
        this.carreteras = new HashMap<>();
        //this.poblados = new HashMap<>();
        this.listaHexagonos = new ArrayList<Hexagono>();
        this.listaNumeros = new ArrayList<Produccion>();
        this.mapaHexagonos = new HashMap<>();
        this.mapaVertices = new HashMap<>();
        this.inicializarHexagonosDefault();
        this.inicializarNumerosDefault();
        this.asignarCoordenadasHexagonos();
        this.asignarNumeroALosHexagonos();
        this.inicializarVertices();
        this.asignarAdyacentesAVertices();
        this.inicializarLadron();
    }

    public Tablero(ArrayList<Hexagono> listaHexagonos, ArrayList<Produccion> listaNumeros) {
        if (listaHexagonos.size() != 19 || listaNumeros.size() != 18) {
            throw new IllegalArgumentException("Cantidad de hexagonos o numeros invalida para el tablero"); //cambiar excepcion
        }
        this.ladron = new Ladron();
        this.listaHexagonos = listaHexagonos;
        this.listaNumeros = listaNumeros;
        this.mapaHexagonos = new HashMap<>();
        this.mapaVertices = new HashMap<>();
        this.asignarCoordenadasHexagonos();
        this.asignarNumeroALosHexagonos();
        this.inicializarVertices();
        this.asignarAdyacentesAVertices();
        this.inicializarLadron();
    }
    
    // public boolean construirCarretera(Jugador jugador, Coordenadas coordenadas) {
    //     if(!jugador.puedeConstruirCarretera()) {
    //         return false;
    //     }
    //     if(!esAdyacenteAConstruccionExistente(jugador, coordenadas)) {
    //         return false;
    //     }
    //     if(carreteras.containsKey(coordenadas)) {
    //         return false;
    //     }
    //     jugador.construirCarretera();
    //     carreteras.put(coordenadas, new Carretera(jugador, coordenadas));
    //     return true;
    // }

    // private boolean esAdyacenteAConstruccionExistente(Jugador jugador, Coordenadas coordenadas) {
    //     // verificar adyacencia a carreteras del mismo jugador
    //     for(Coordenadas coordVecinas : coordenadas.getVecinas()) {
    //         Carretera carreteraVecina = carreteras.get(coordVecinas);
    //         if(carreteraVecina != null && carreteraVecina.getJugador().equals(jugador)) {
    //             return true;
    //     }
    //     // verificar adyacencia a poblados del mismo jugador
    //     for(Coordenadas coordVecinas : coordenadas.getVecinas()) {
    //         Poblado pobladoVecino = poblados.get(coordVecinas);
    //         if(pobladoVecino != null && pobladoVecino.getJugador().equals(jugador)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    // public boolean hayCarreteraEn(Coordenadas coordenadas, Jugador jugador) {
    //     Carretera carretera = carreteras.get(coordenadas);
    //     return (carretera != null && carretera.getJugador().equals(jugador));
    // }

    // public void construirPoblado(Jugador jugador, Coordenadas coordenadas) {
    //     if(poblados.containsKey(coordenadas)) {
    //         throw new IllegalArgumentException("Ya existe un poblado en esas coordenadas.\n"); //cambiar excepcion
    //     }
    //     jugador.construirPoblado();
    //     poblados.put(coordenadas, new Poblado(jugador, coordenadas));
    // }

    public List<Hexagono> obtenerHexagonos() {
        return new ArrayList<>(mapaHexagonos.values());
    }

    // crea una lista con 19 hexagonos por default
    private void inicializarHexagonosDefault() {
        listaHexagonos = new ArrayList<Hexagono>(List.of(
            new Desierto(),
            new Campo(), new Campo(), new Campo(), new Campo(),
            new Bosque(), new Bosque(), new Bosque(), new Bosque(),
            new Pastizal(), new Pastizal(), new Pastizal(), new Pastizal(),
            new Colina(), new Colina(), new Colina(),
            new Montana(), new Montana(), new Montana()
        ));
    }

    private void inicializarNumerosDefault() {
        listaNumeros = new ArrayList<Produccion>(List.of(
            new Produccion(2),
            new Produccion(3), new Produccion(3),
            new Produccion(4), new Produccion(4),
            new Produccion(5), new Produccion(5),
            new Produccion(6), new Produccion(6),
            new Produccion(8), new Produccion(8),
            new Produccion(9), new Produccion(9),
            new Produccion(10), new Produccion(10),
            new Produccion(11), new Produccion(11),
            new Produccion(12)
        ));
    }

    private void asignarCoordenadasHexagonos() {

        List<Coordenadas> coord = this.crearListaDeCoordenadasHexagonos(); //mismo len que hexagonos (19)

        int i = 0;
        for (; i < listaHexagonos.size() ; i++) {
                Coordenadas coords = coord.get(i);
                mapaHexagonos.put(coords, listaHexagonos.get(i));
            }
        }

    private ArrayList<Coordenadas> crearListaDeCoordenadasHexagonos() {
        ArrayList<Coordenadas> coords = new ArrayList<>();

        int[] cantPorFila = { 3, 4, 5, 4, 3 };
        int[] inicioPorFila = { 2, 1, 0, 1, 2 };

        for (int fila = 0; fila < cantPorFila.length; fila++) {
            int cantidad = cantPorFila[fila];
            int inicio = inicioPorFila[fila];

            for (int j = 0; j < cantidad; j++) {
                int col = inicio + j * 2;
                coords.add(new Coordenadas(fila, col));
            }
        }
        return coords;
    }
    
    private void asignarNumeroALosHexagonos() {
        int indiceHexagonos = 0;
        int indiceNumeros = 0;
        for (; indiceHexagonos < listaHexagonos.size() && indiceNumeros < listaNumeros.size();) {
            Hexagono hexagono = listaHexagonos.get(indiceHexagonos);
            if (!hexagono.esDesierto()) {
                Produccion produccion = (Produccion)listaNumeros.get(indiceNumeros);
                hexagono.asignarProduccion(produccion);
                indiceNumeros++;
            }
            indiceHexagonos++;
        }
    }

    private void inicializarVertices() {
        // recorrer hexagonos y obtener vertices
        for (Map.Entry<Coordenadas, Hexagono> entrada : mapaHexagonos.entrySet()) {
            Hexagono hexagono = entrada.getValue();
            Coordenadas coords = entrada.getKey();
            Coordenadas[] coordsVertices = this.obtenerVerticesDeHexagono(hexagono, coords);
            for (Coordenadas coordVertice : coordsVertices) {
                if (!mapaVertices.containsKey(coordVertice)) {
                    Vertice vertice = new Vertice();
                    vertice.agregarHexagono(hexagono);
                    mapaVertices.put(coordVertice, vertice);
                } else {
                //el vertice ya existe:
                    Vertice verticeExistente = mapaVertices.get(coordVertice);
                    verticeExistente.agregarHexagono(hexagono);
                }
            }
        }
    }

    private Coordenadas[] obtenerVerticesDeHexagono(Hexagono hexagono, Coordenadas coordenadas) {
        int hx = coordenadas.obtenerCoordenadaX();
        int hy = coordenadas.obtenerCoordenadaY();

        Coordenadas[] coordenadasVertices = new Coordenadas[] {
                new Coordenadas(hx, hy),
                new Coordenadas(hx, hy - 1),
                new Coordenadas(hx + 1, hy - 1),
                new Coordenadas(hx + 1, hy),
                new Coordenadas(hx + 1, hy + 1),
                new Coordenadas(hx, hy + 1)
        };
        return coordenadasVertices;
    }

    private void asignarAdyacentesAVertices() {
        for (Map.Entry<Coordenadas, Vertice> entrada : mapaVertices.entrySet()) {
            Coordenadas coordVertice = entrada.getKey();
            Vertice vertice = entrada.getValue();
            List<Coordenadas> coordsAdyacentes = this.calcularCoordenadasAdyacentesDeVertice(coordVertice);
            for (Coordenadas coordAdyacente : coordsAdyacentes) {
                if (mapaVertices.containsKey(coordAdyacente)){
                    vertice.agregarAdyacente(mapaVertices.get(coordAdyacente));
                }
            }
        }
    }

    private List<Coordenadas> calcularCoordenadasAdyacentesDeVertice(Coordenadas coordVertice) {
        int x = coordVertice.obtenerCoordenadaX();
        int y = coordVertice.obtenerCoordenadaY();

        List<Coordenadas> coordsAdyacentes = new ArrayList<>();

        int suma = x + y;
        if (suma % 2 == 0) {
            coordsAdyacentes.add(new Coordenadas(x - 1, y));
            coordsAdyacentes.add(new Coordenadas(x, y - 1));
            coordsAdyacentes.add(new Coordenadas(x, y + 1));
        } else {
            coordsAdyacentes.add(new Coordenadas(x, y - 1));
            coordsAdyacentes.add(new Coordenadas(x, y + 1));
            coordsAdyacentes.add(new Coordenadas(x + 1, y));
        }

        return coordsAdyacentes;
    }

    private void inicializarLadron() {
        List<Hexagono> hexagonos = new ArrayList<>(mapaHexagonos.values());
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.esDesierto()) {
                ladron.moverA(hexagono);
            }
        }
    }
    
    public boolean sePuedeConstruirInicial(Coordenadas coordenadas, Construccion construccion) {
        if (construccion == null) {
            throw new IllegalArgumentException("Construccion nula"); //cambiar excepcion
        }
        if (!construccion.esPoblado()){
            throw new IllegalArgumentException("Solo se pueden construir poblados en la colocacion inicial"); //cambiar excepcion
        }
        if (!this.sonCoordenadasValidas(coordenadas)) {
            throw new IllegalArgumentException("Coordenadas invalidas"); //cambiar excepcion
        }
        Vertice vertice = mapaVertices.get(coordenadas);
        return vertice.cumpleReglaDistancia() && !vertice.tieneConstruccion();
    }

    public ArrayList<Recurso> colocarConstruccionInicial(Construccion construccion, Coordenadas coordenadas, String jugador){
        if (!this.sePuedeConstruirInicial(coordenadas, construccion)){
            throw new PosInvalidaParaConstruirException();
        }
        Vertice vertice = mapaVertices.get(coordenadas);
        var recursos = vertice.construirInicial(construccion, jugador);
        return recursos;
    }

    public void mezclarHexagonos(){
        var hexagonosMezclados = new ArrayList<Hexagono>(this.listaHexagonos);
        Collections.shuffle(hexagonosMezclados);
        this.listaHexagonos = hexagonosMezclados;
        this.asignarNumeroALosHexagonos();
    }

    public void mezclarNumeros(){
        var numerosMezclados = new ArrayList<Produccion>(this.listaNumeros);
        Collections.shuffle(numerosMezclados);
        this.listaNumeros = numerosMezclados;
        this.asignarNumeroALosHexagonos();
    }

    public boolean sonCoordenadasValidas(Coordenadas coordenadas) {
        return mapaVertices.containsKey(coordenadas);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tablero otro = (Tablero) obj;
        return listaHexagonos.equals(otro.listaHexagonos) &&
               listaNumeros.equals(otro.listaNumeros);
    }

    public void colocarEn(Coordenadas coordenadas, Construccion construccion, String jugador){
        Vertice vertice = mapaVertices.get(coordenadas);

        if (vertice != null){
            vertice.construir(construccion, jugador);
        } else {
            throw new IllegalArgumentException("Coordenadas invalidas"); //cambiar excepcion
        }
    }

    public ArrayList<Recurso> producirRecurso(int produccionDado, String jugador){
        ArrayList<Recurso> produccionDelJugador = new ArrayList<>();
        for(Vertice vertice : mapaVertices.values()){
            if(vertice.tieneConstruccion() && vertice.esDueno(jugador)){
                produccionDelJugador.addAll(vertice.producirRecursos(produccionDado));
            }
        }
        return produccionDelJugador;
    }

    public boolean sePuedeConstruir(Coordenadas coordenadas, Construccion construccion) {
        if (construccion == null) {
            throw new IllegalArgumentException("Construccion nula"); //cambiar excepcion
        }
        if (!this.sonCoordenadasValidas(coordenadas)) {
            throw new IllegalArgumentException("Coordenadas invalidas"); //cambiar excepcion
        }
        Vertice vertice = mapaVertices.get(coordenadas);
        return vertice.cumpleReglaDistancia() && !vertice.tieneConstruccion();
    }
}
/*  
 * NUEVA ESTRUCTURA DE TABLERO:
 * TABLERO CONOCE HEXAGONOS Y VERTICES Y SUS COORDENADAS JUNTO CON UNA REFERENCIA AL LADRON
 * CALCULA ADYACENTES A VERTICES Y SE LOS ASIGNA
 * HEXAGONOS NO CONOCEN SUS VERTICES, SOLO DEVUELVEN SU RECURSO
 * LOS CALCULOS DE COORDENADAS LOS HACE TABLERO
 * COMO HAGO PARA QUE CADA JUGADOR SEPA DE SUS CONSTRUCCIONES? LAS ALMACENA JUEGO? TABLERO?
 * 
 * INICIALIZACION:
 * TABLERO INICIALIZA HEXAGONOS CON SUS COORDENADAS Y TIPOS
 * TABLERO CALCULA COORDENADAS DE VERTICES CON SUS RESPECTIVOS HEXAGONOS ADYACENTES
 * TABLERO CALCULA ADYACENTES DE CADA VERTICE Y SE LOS ASIGNA
 * LOS VERTICES NO SABEN SUS COORDENADAS, SOLO EL TABLERO
 * TABLERO INICIALIZA LADRON EN HEXAGONO DESIERTO
 * 
 */
