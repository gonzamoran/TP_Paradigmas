package edu.fiuba.algo3.modelo;

import java.util.List;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Collections;
import edu.fiuba.algo3.modelo.Hexagono;
import edu.fiuba.algo3.modelo.Ladron;
import edu.fiuba.algo3.modelo.Vertice;
import edu.fiuba.algo3.modelo.TipoRecurso;

public class Tablero {
    private Ladron ladron;
    private Map<Coordenadas, Hexagono> mapaHexagonos;
    private Map<Coordenadas, Vertice> mapaVertices;

    public Tablero() {
        this.ladron = new Ladron();
        this.mapaHexagonos = new HashMap<>();
        this.mapaVertices = new HashMap<>();
        this.inicializarHexagonos();
        this.asignarNumeroALosHexagonos();
        this.inicializarVertices();
        this.inicializarLadron();
    }
    // crear lista de coordenadas, shufflear y asignar a hexagonos

    public List<Hexagono> obtenerHexagonos() {
        return this.mapaHexagonos.values().stream().toList();
    }

    public void /* Construccion */ construirEnCoordenada(Coordenadas unaCoordenada) {

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

    private void inicializarHexagonos() {
        // logica para inicializar coordenada, hexagono
        List<Coordenadas> coord = this.crearListaDeCoordenadasHexagonos();
        Collections.shuffle(coord);

        int indiceCoord = 0;
        TipoRecurso[] tipoRecursos = { TipoRecurso.DESIERTO, TipoRecurso.GRANO, TipoRecurso.MADERA, TipoRecurso.LANA,
                TipoRecurso.LADRILLO, TipoRecurso.MINERAL };
        int[] cantidades = { 1, 4, 4, 4, 3, 3 };
        for (int i = 0; i < tipoRecursos.length; i++) {
            for (int j = 0; j < cantidades[i]; j++) {
                Coordenadas coords = coord.get(indiceCoord);
                Hexagono hexagono = new Hexagono(indiceCoord, tipoRecursos[i], coords);
                mapaHexagonos.put(coords, hexagono);
                indiceCoord += 1;
            }
        }
    }

    private void asignarNumeroALosHexagonos() {
        List<Integer> numeros = List.of(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12);
        List<Hexagono> hexagonos = new ArrayList<>(mapaHexagonos.values());
        Collections.shuffle(numeros);
        for (int i = 0; i < numeros.size(); i++) {
            Hexagono hexagono = hexagonos.get(i);
            if (!hexagono.esDesierto()) {
                hexagono.asignarNumero(i);
            }
        }
    }

    private void inicializarLadron() {
        List<Hexagono> hexagonos = new ArrayList<>(mapaHexagonos.values());
        for (Hexagono hexagono : hexagonos) {
            if (hexagono.esDesierto()) {
                ladron.moverA(hexagono/* cambiar a coordenadas de hexagono */ );
            }
        }
    }

    private void inicializarVertices() { // modificar
        // recorrer hexagonos y obtener vertices
        List<Hexagono> hexagonos = new ArrayList<>(mapaHexagonos.values());
        for (Hexagono hexagono : hexagonos) {
            hexagono.obtenerVertices();
            // agregar vertices al mapa de vertices si no existen
        }
    }

}
/*
 * 1 desierto. 4 Trigo/grano, 4 madera, 4 oveja/lana, 3 arcilla/ladrillo, 3
 * piedra/minera.
 * 
 * cada hexagono tiene coordenadas x,y.
 * a partir de ellas se calculan los vertices con:
 * x,y
 * / \
 * x,y-1/ \ x,y+1
 * | |
 * | |
 * x+1,y-1 \ / x+1,y+1
 * \ /
 * x+1,y
 * 
 * 
 * Numeros:
 * 2
 * 3,3
 * 4,4
 * 5,5
 * 6,6
 * 8,8
 * 9,9
 * 10,10
 * 11,11
 * 12
 */
