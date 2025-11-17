package edu.fiuba.algo3.modelo.tablero;

// import java.system.out;
import java.util.List;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Ladron;
import edu.fiuba.algo3.modelo.tablero.TipoRecurso;
import edu.fiuba.algo3.modelo.tablero.Vertice;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.excepciones.PosInvalidaParaConstruirException;

import java.util.HashMap;
import java.util.Collections;

public class Tablero {
    private Ladron ladron;
    private Jugador jugador;
    private ArrayList<Hexagono> listaHexagonos;
    private ArrayList<Produccion> listaNumeros;
    private Map<Coordenadas, Hexagono> mapaHexagonos;
    private Map<Coordenadas, Vertice> mapaVertices;

    public Tablero(ArrayList<Hexagono> listaHexagonos, ArrayList<Produccion> listaNumeros) {
        this.ladron = new Ladron();
        this.listaHexagonos = listaHexagonos;
        this.listaNumeros = listaNumeros;
        this.mapaHexagonos = new HashMap<>();
        this.mapaVertices = new HashMap<>();
        // this.inicializarHexagonos();
        // this.asignarNumeroALosHexagonos();
        // this.inicializarVertices();
        // this.asignarAdyacentesAVertices();
        this.inicializarLadron();
    }
    

    public List<Hexagono> obtenerHexagonos() {
        return new ArrayList<>(mapaHexagonos.values());
    }

    // // crear lista de coordenadas, shufflear y asignar a hexagonos
    // private void inicializarHexagonos() {
    //     // logica para inicializar coordenada, hexagono
    //     List<Coordenadas> coord = this.crearListaDeCoordenadasHexagonos();
    //     Collections.shuffle(coord);

    //     int indiceCoord = 0;
    //     TipoRecurso[] tipoRecursos = { TipoRecurso.DESIERTO, TipoRecurso.GRANO, TipoRecurso.MADERA, TipoRecurso.LANA,
    //             TipoRecurso.LADRILLO, TipoRecurso.MINERAL };
    //     int[] cantidades = { 1, 4, 4, 4, 3, 3 };
    //     //asignacion de tipos e id a cada hexagono, no coordenadas
    //     for (int i = 0; i < tipoRecursos.length; i++) {
    //         for (int j = 0; j < cantidades[i]; j++) {
    //             Coordenadas coords = coord.get(indiceCoord);
    //             Hexagono hexagono = new Hexagono(indiceCoord, tipoRecursos[i]);
    //             mapaHexagonos.put(coords, hexagono);
    //             indiceCoord += 1;
    //         }
    //     }
    // }

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
    

    // private void asignarNumeroALosHexagonos() {
    //     List<Integer> numeros = new (List.of(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12));
    //     List<Hexagono> hexagonos = new ArrayList<>(mapaHexagonos.values());
    //     Collections.shuffle(numeros);
    //     int indiceHexagonos = 0;
    //     int indiceNumeros = 0;
    //     for (; indiceHexagonos < hexagonos.size() && indiceNumeros < numeros.size();) {
    //         Hexagono hexagono = hexagonos.get(indiceHexagonos);
    //         if (!hexagono.esDesierto()) {
    //             hexagono.asignarNumero(numeros.get(indiceNumeros));
    //             indiceNumeros++;
    //         }
    //         indiceHexagonos++;
    //     }
    // }

    // private void inicializarVertices() {
    //     // recorrer hexagonos y obtener vertices
    //     for (Map.Entry<Coordenadas, Hexagono> entrada : mapaHexagonos.entrySet()) {
    //         Hexagono hexagono = entrada.getValue();
    //         Coordenadas coords = entrada.getKey();
    //         Coordenadas[] coordsVertices = this.obtenerVerticesDeHexagono(hexagono, coords);
    //         for (Coordenadas coordVertice : coordsVertices) {
    //             //el vertice no existe:
    //             if (!mapaVertices.containsKey(coordVertice)) {
    //                 Vertice vertice = new Vertice();
    //                 vertice.agregarHexagono(hexagono);
    //                 hexagono.agregarVerticeAdyacente(vertice);
    //                 mapaVertices.put(coordVertice, vertice);
    //             } else {
    //             //el vertice ya existe:
    //                 Vertice verticeExistente = mapaVertices.get(coordVertice);
    //                 verticeExistente.agregarHexagono(hexagono);
    //                 hexagono.agregarVerticeAdyacente(verticeExistente);
    //             }
    //         }
    //     }
    // }

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

    public void colocarConstruccionInicial(int x, int y, Jugador jugador) {
        Vertice verticeAConstruir = null;
        boolean encontrado = false;
        for (Map.Entry<Coordenadas, Vertice> entrada : mapaVertices.entrySet()) {
            Coordenadas coords = entrada.getKey();
            if ((coords.obtenerCoordenadaX() == x) && (coords.obtenerCoordenadaY() == y)) {
                verticeAConstruir = entrada.getValue();
                verticeAConstruir.construirEn("Poblado", jugador);
                encontrado = true;
            }
        }
        if (!encontrado) {
            //throw new PosInvalidaParaConstruirException();
            throw new IllegalArgumentException("No se encontró un vértice en las coordenadas dadas.");
        }
    }

    public void mezclarHexagonos(){
        var hexagonosMezclados = new ArrayList<Hexagono>(this.listaHexagonos);
        Collections.shuffle(hexagonosMezclados);
        this.listaHexagonos = hexagonosMezclados;
    }

    public void mezclarNumeros(){
        var numerosMezclados = new ArrayList<Produccion>(this.listaNumeros);
        Collections.shuffle(numerosMezclados);
        this.listaNumeros = numerosMezclados;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Tablero otro = (Tablero) obj;
        return listaHexagonos.equals(otro.listaHexagonos) &&
               listaNumeros.equals(otro.listaNumeros);
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
