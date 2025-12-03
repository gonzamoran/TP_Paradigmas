package edu.fiuba.algo3.modelo.tablero;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
// import java.system.out;
import java.util.*;

import edu.fiuba.algo3.modelo.Recurso;
import edu.fiuba.algo3.modelo.tablero.*;
import edu.fiuba.algo3.modelo.construcciones.*;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;
import edu.fiuba.algo3.modelo.Banca;

import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;
import edu.fiuba.algo3.modelo.tiposBanca.*;
import edu.fiuba.algo3.modelo.tiposRecurso.*;

import edu.fiuba.algo3.modelo.excepciones.PosInvalidaParaConstruirException;
import edu.fiuba.algo3.modelo.excepciones.NoEsPosibleConstruirException;

public class Tablero {
    private Ladron ladron; // sacar
    private ArrayList<Carretera> listaCarreteras;
    private ArrayList<Hexagono> listaHexagonos;
    private ArrayList<Produccion> listaNumeros;
    private Map<Coordenadas, Hexagono> mapaHexagonos;
    private Map<Coordenadas, Vertice> mapaVertices;

    private Jugador propietarioCarreteraMasLarga = null;

    public Tablero() {
        this.listaHexagonos = new ArrayList<Hexagono>();
        this.listaNumeros = new ArrayList<Produccion>();
        this.mapaHexagonos = new HashMap<>();
        this.mapaVertices = new HashMap<>();
        this.listaCarreteras = new ArrayList<>();
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
            throw new IllegalArgumentException("Cantidad de hexagonos o numeros invalida para el tablero");
        }
        this.listaHexagonos = listaHexagonos;
        this.listaNumeros = listaNumeros;
        this.mapaHexagonos = new HashMap<>();
        this.mapaVertices = new HashMap<>();
        this.listaCarreteras = new ArrayList<>();
        this.asignarCoordenadasHexagonos();
        this.asignarNumeroALosHexagonos();
        this.inicializarVertices();
        this.asignarAdyacentesAVertices();
        this.inicializarLadron();
    }

    private void inicializarHexagonosDefault() {
        listaHexagonos = new ArrayList<Hexagono>(List.of(
                new Desierto(),
                new Campo(), new Campo(), new Campo(), new Campo(),
                new Bosque(), new Bosque(), new Bosque(), new Bosque(),
                new Pastizal(), new Pastizal(), new Pastizal(), new Pastizal(),
                new Colina(), new Colina(), new Colina(),
                new Montana(), new Montana(), new Montana()));
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
                new Produccion(12)));
    }

    private void asignarCoordenadasHexagonos() {

        List<Coordenadas> coords = this.crearListaDeCoordenadasHexagonos(); // mismo len que hexagonos (19)

        for (int i = 0; i < listaHexagonos.size(); i++) {
            Coordenadas coord = coords.get(i);
            mapaHexagonos.put(coord, listaHexagonos.get(i));
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
            // !! sacar esDesierto y sobreescribir asignarProduccion en Desierto
            if (!hexagono.esDesierto()) {
                Produccion produccion = (Produccion) listaNumeros.get(indiceNumeros);
                hexagono.asignarProduccion(produccion);
                indiceNumeros++;
            }
            indiceHexagonos++;
        }
    }

    private void inicializarVertices() {
        Map<Coordenadas, Banca> mapaBancas = this.inicializarBancas();
        // recorrer hexagonos y obtener vertices
        for (Map.Entry<Coordenadas, Hexagono> entrada : mapaHexagonos.entrySet()) {
            Hexagono hexagono = entrada.getValue();
            Coordenadas coords = entrada.getKey();
            Coordenadas[] coordsVertices = this.obtenerVerticesDeHexagono(coords);
            for (Coordenadas coordVertice : coordsVertices) {
                if (!mapaVertices.containsKey(coordVertice)) {
                    Vertice vertice = new Vertice();
                    vertice.agregarHexagono(hexagono);
                    vertice.agregarBanca(mapaBancas.get(coordVertice));
                    mapaVertices.put(coordVertice, vertice);
                } else {
                    // el vertice ya existe:
                    Vertice verticeExistente = mapaVertices.get(coordVertice);
                    verticeExistente.agregarHexagono(hexagono);
                }
            }
        }
    }

    private Map<Coordenadas, Banca> inicializarBancas() {
        Map<Coordenadas, Banca> mapaBancas = new HashMap<>();
        List<Coordenadas> coordenadasPuertos = new ArrayList<>(List.of(
                new Coordenadas(0, 1), new Coordenadas(0, 2),
                new Coordenadas(1, 0), new Coordenadas(2, 0),
                new Coordenadas(3, 0), new Coordenadas(4, 0),
                new Coordenadas(5, 1), new Coordenadas(5, 2),
                new Coordenadas(5, 4), new Coordenadas(5, 5),
                new Coordenadas(4, 7), new Coordenadas(4, 8),
                new Coordenadas(3, 9), new Coordenadas(2, 9),
                new Coordenadas(1, 8), new Coordenadas(1, 7),
                new Coordenadas(0, 4), new Coordenadas(0, 5)));

        List<Banca> bancas = new ArrayList<>(List.of(
                new Banca3a1(), new Banca3a1(),
                new Banca2a1(new Madera()), new Banca2a1(new Grano()),
                new Banca3a1(), new Banca2a1(new Piedra()),
                new Banca3a1(), new Banca2a1(new Lana()),
                new Banca2a1(new Ladrillo())));
        int indiceBancas = 0;
        for (int i = 0; i < coordenadasPuertos.size() - 2; i += 2) {
            mapaBancas.put(coordenadasPuertos.get(i), bancas.get(indiceBancas));
            mapaBancas.put(coordenadasPuertos.get(i + 1), bancas.get(indiceBancas));
            indiceBancas++;
        }
        return mapaBancas;
    }

    private Coordenadas[] obtenerVerticesDeHexagono(Coordenadas coordenadas) {
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
                if (mapaVertices.containsKey(coordAdyacente)) {
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
                this.ladron = new Ladron(hexagono);
            }
        }
    }

    public boolean sePuedeConstruirInicial(Coordenadas coordenadas, Construccion construccion) {
        if (construccion == null) {
            return false;
        }
        if (!construccion.equals(new Poblado())) {
            return false;
        }

        if (!this.sonCoordenadasValidas(coordenadas)) {
            return false;
        }

        Vertice vertice = mapaVertices.get(coordenadas);
        return vertice.cumpleReglaDistancia() && !vertice.tieneConstruccion();
    }

    public ArrayList<Recurso> colocarConstruccionInicial(Construccion construccion, Coordenadas coordenadas,
            Jugador jugador) {
        if (!this.sePuedeConstruirInicial(coordenadas, construccion)) {
            throw new PosInvalidaParaConstruirException();
        }
        Vertice vertice = mapaVertices.get(coordenadas);
        var recursos = vertice.construirInicial(construccion, jugador);
        return recursos;
    }

    public void mezclarHexagonos() {
        var hexagonosMezclados = new ArrayList<Hexagono>(this.listaHexagonos);
        Collections.shuffle(hexagonosMezclados);
        this.listaHexagonos = hexagonosMezclados;
        this.asignarNumeroALosHexagonos();
    }

    public void mezclarNumeros() {
        var numerosMezclados = new ArrayList<Produccion>(this.listaNumeros);
        Collections.shuffle(numerosMezclados);
        this.listaNumeros = numerosMezclados;
        this.asignarNumeroALosHexagonos();
    }

    public boolean sonCoordenadasValidas(Coordenadas coordenadas) {
        return mapaVertices.containsKey(coordenadas);
    }

    public void colocarEn(Coordenadas coordenadas, Construccion construccion, Jugador jugador) {
        if (!this.sePuedeConstruir(coordenadas, construccion, jugador)) {
            throw new NoEsPosibleConstruirException();
        }
        Vertice vertice = mapaVertices.get(coordenadas);
        if (vertice != null) {
            vertice.construir(construccion, jugador);
        } else {
            throw new IllegalArgumentException("Coordenadas invalidas"); // cambiar excepcion
        }

    }

    public ArrayList<Recurso> producirRecurso(int produccionDado, Jugador jugador) {
        ArrayList<Recurso> produccionDelJugador = new ArrayList<>();
        for (Vertice vertice : mapaVertices.values()) {
            if (vertice.tieneConstruccion() && vertice.esDueno(jugador)) {
                produccionDelJugador.addAll(vertice.producirRecursos(produccionDado));
            }
        }
        return produccionDelJugador;
    }


    public boolean sePuedeConstruir(Coordenadas coordenadas, Construccion construccion, Jugador jugador) {
        if (construccion == null) {
            throw new IllegalArgumentException("Construccion nula"); // cambiar excepcion
        }
        if (!this.sonCoordenadasValidas(coordenadas)) {
            throw new PosInvalidaParaConstruirException();
        }
        Vertice vertice = mapaVertices.get(coordenadas);

        if (!jugador.poseeRecursosParaConstruir(construccion)) {
            return false;
        }

        if (vertice.cumpleReglaDistancia()) {
            return vertice.puedeConstruirse(construccion);
        }
        return false;
    }

    public void moverLadronA(Coordenadas coordenadasHexagono) {
        var jugadoresAfectados = this.obtenerJugadoresAdyacentes(coordenadasHexagono);
        this.ladron.moverLadronA(this.obtenerHexagono(coordenadasHexagono), jugadoresAfectados);
    }

    public List<Jugador> obtenerJugadoresAdyacentes(Coordenadas coordenadasHexagono) {
        List<Jugador> jugadores = new ArrayList<>();

        Coordenadas[] coordsVertices = this.obtenerVerticesDeHexagono(coordenadasHexagono);

        List<Vertice> vertices = new ArrayList<>();
        for (Coordenadas coord : coordsVertices) {
            vertices.add(mapaVertices.get(coord));
        }

        for (Vertice vertice : vertices) {
            if (vertice.tieneConstruccion()) {
                Jugador dueno = vertice.obtenerDueno();
                jugadores.add(dueno);
            }
        }
        return jugadores;
    }

    public ArrayList<Recurso> ladronRobaRecurso(Jugador jugadorActual, ProveedorDeDatos proveedor) {
        ArrayList<Recurso> recursoRobado = ladron.robarRecurso(jugadorActual, proveedor);
        if (!recursoRobado.isEmpty()) {
            jugadorActual.agregarRecurso(recursoRobado.get(0));
        }
        return recursoRobado;
    }

    public Hexagono obtenerHexagono(Coordenadas coordenadas) {
        return mapaHexagonos.get(coordenadas);
    }

    public Hexagono obtenerHexagonoLadron() {
        return ladron.obtenerHexagonoActual();
    }

    public boolean estaConstruidoCon(Construccion construccion, Coordenadas coordenadas, Jugador jugador) {
        Vertice vertice = mapaVertices.get(coordenadas);
        return vertice.estaConstruidoCon(construccion, jugador);
    }

    public void construirCarretera(Coordenadas coordenadaExtremo1, Coordenadas coordenadaExtremo2, Jugador jugador) {
        if (!this.sonCoordenadasValidas(coordenadaExtremo1) || !this.sonCoordenadasValidas(coordenadaExtremo2)) {
            throw new PosInvalidaParaConstruirException();
        }
        Vertice vertice1 = mapaVertices.get(coordenadaExtremo1);
        Vertice vertice2 = mapaVertices.get(coordenadaExtremo2);

        if (!vertice1.esAdyacente(vertice2)) {
            throw new PosInvalidaParaConstruirException();
        }

        if (!vertice1.poseeCarreterasDe(jugador) && !vertice2.poseeCarreterasDe(jugador)
                && !(vertice1.esDueno(jugador) || vertice2.esDueno(jugador))) {
            throw new NoEsPosibleConstruirException();
        }

        var carretera = new Carretera();
        if (!vertice1.puedeConstruirse(carretera) && !vertice2.puedeConstruirse(carretera)) {
            throw new NoEsPosibleConstruirException();
        }

        if (!jugador.poseeRecursosParaConstruir(carretera)) {
            throw new NoEsPosibleConstruirException();
        }

        // consumir recursos
        List<Recurso> recursosNecesarios = carretera.obtenerRecursosNecesarios();
        for (Recurso recurso : recursosNecesarios) {
            jugador.removerRecurso(recurso);
        }

        vertice1.construirCarretera(carretera, jugador);
        vertice2.construirCarretera(carretera, jugador);
        jugador.agregarConstruccion(carretera);
        this.listaCarreteras.add(carretera);
    }

    public List<Banca> obtenerBancasDisponibles(Jugador jugador) {
        List<Banca> bancasDisponibles = new ArrayList<>();
        for (Vertice vertice : mapaVertices.values()) {
            if (vertice.tieneConstruccion() && vertice.esDueno(jugador)) {
                List<Banca> disponibles = vertice.obtenerBancasDisponibles();
                bancasDisponibles.addAll(disponibles);
            }
        }
        bancasDisponibles.add(new Banca4a1());
        return bancasDisponibles;
    }

    public void construirCarreteraGratis(Coordenadas coordenadaExtremo1, Coordenadas coordenadaExtremo2,
            Jugador jugador) {
        if (!this.sonCoordenadasValidas(coordenadaExtremo1) || !this.sonCoordenadasValidas(coordenadaExtremo2)) {
            throw new PosInvalidaParaConstruirException();
        }

        Vertice vertice1 = mapaVertices.get(coordenadaExtremo1);
        Vertice vertice2 = mapaVertices.get(coordenadaExtremo2);

        if (!vertice1.esAdyacente(vertice2)) {
            throw new PosInvalidaParaConstruirException();
        }

        if (!vertice1.poseeCarreterasDe(jugador) && !vertice2.poseeCarreterasDe(jugador)
                && !(vertice1.esDueno(jugador) || vertice2.esDueno(jugador))) {
            throw new NoEsPosibleConstruirException();
        }

        var carretera = new Carretera();
        if (!vertice1.puedeConstruirse(carretera) && !vertice2.puedeConstruirse(carretera)) {
            throw new NoEsPosibleConstruirException();
        }

        vertice1.construirCarretera(carretera, jugador);
        vertice2.construirCarretera(carretera, jugador);
        jugador.agregarConstruccion(carretera);
        this.listaCarreteras.add(carretera);
    }

    public int obtenerCarreteraMasLarga(Jugador jugador) {
        Set<Vertice> visitadosComponentes = new HashSet<>();
        int mejor = 0;

        for (Carretera carretera : listaCarreteras) {
            if (!carretera.esDueno(jugador)){ 
                continue;
            }

            List<Vertice> vertices = carretera.conseguirVertices();
            Vertice v1 = vertices.get(0);
            Vertice v2 = vertices.get(1);

            if (v2.tieneConstruccion() && !v2.esDueno(jugador)) {
                if (mejor < 1) {
                    mejor = 1;
                }
                continue;
            }

            if (visitadosComponentes.contains(v1) || visitadosComponentes.contains(v2)) {
                continue;
            }

            Set<Vertice> visitados = new HashSet<>();
            Vertice extremo1 = dfsBuscarExtremo(v1, jugador, visitados);
            Vertice extremo2 = dfsBuscarExtremo(v2, jugador, visitados);

            visitadosComponentes.addAll(visitados);

            int largo;

            if (extremo1 == null) {
                largo = dfsLongitud(v1, null,  jugador);
            } else {
                largo = dfsLongitud(extremo1, null, jugador);
            }
            if (largo > mejor) {
                mejor = largo;
            }
            if (extremo2 == null) {
                largo = dfsLongitud(v2, null,  jugador);
            } else {
                largo = dfsLongitud(extremo2, null, jugador);
            }
            if (largo > mejor) {
                mejor = largo;
            }
        }
        return mejor;
    }

    private Vertice dfsBuscarExtremo(Vertice actual, Jugador jugador, Set<Vertice> visitados) {
        visitados.add(actual);

        if (actual.esExtremoReal(jugador)) {
            return actual;
        }

        for (Vertice ady : actual.verticesConCarreterasAdyacentes(jugador)) {
            if (!visitados.contains(ady)) {
                Vertice res = dfsBuscarExtremo(ady, jugador, visitados);
                if (res != null) {
                    return res;
                }
            }
        }
        return null;
    }

    private int dfsLongitud(Vertice actual, Vertice padre, Jugador jugador) {
        int mejor = 0;

        for (Vertice ady : actual.verticesConCarreterasAdyacentes(jugador)) {
            if (ady.tieneConstruccion() && !ady.esDueno(jugador)) {
                mejor = Math.max(mejor, 1);
                continue;
            }

            if (ady != padre) {
                mejor = Math.max(mejor, 1 + dfsLongitud(ady, actual, jugador));
            }
        }
        return mejor;
    }
    public void gestionarCaminoMasLargo(Jugador nuevoCandidato){
        int nuevaLongitud = nuevoCandidato.obtenerCaminoMasLargoDelJugador();

        if(propietarioCarreteraMasLarga != null && propietarioCarreteraMasLarga.equals(nuevoCandidato)){
            if (nuevaLongitud < 5){
                propietarioCarreteraMasLarga.quitarCartaGranRutaComercial();
                propietarioCarreteraMasLarga = null;
            }
            return;
        }

        if (propietarioCarreteraMasLarga == null){
            if (nuevaLongitud >= 5){
                nuevoPoseedorGranRutaComercial(nuevoCandidato);
                nuevoCandidato.otorgarGranRutaComercial();
            } 
            return;
        }

        int recordDelPropietario = propietarioCarreteraMasLarga.obtenerCaminoMasLargoDelJugador();

        if (nuevaLongitud > recordDelPropietario){
            propietarioCarreteraMasLarga.quitarCartaGranRutaComercial();
            nuevoCandidato.otorgarGranRutaComercial();
            nuevoPoseedorGranRutaComercial(nuevoCandidato);
        }
    }

    private void nuevoPoseedorGranRutaComercial(Jugador jugador){
        this.propietarioCarreteraMasLarga = jugador;
    }

    

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Tablero otro = (Tablero) obj;
        return listaHexagonos.equals(otro.listaHexagonos) && listaNumeros.equals(otro.listaNumeros);
    }

}
