package edu.fiuba.algo3.modelo.tablero;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
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

import edu.fiuba.algo3.modelo.reglas.*;

import edu.fiuba.algo3.modelo.excepciones.PosInvalidaParaConstruirException;
import edu.fiuba.algo3.modelo.excepciones.NoEsPosibleConstruirException;
import edu.fiuba.algo3.modelo.excepciones.CantidadInvalidaDeHexagonosONumerosException;

public class Tablero {
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
    }

    public Tablero(ArrayList<Hexagono> listaHexagonos, ArrayList<Produccion> listaNumeros) {
        if (listaHexagonos.size() != 19 || listaNumeros.size() != 18) {
            throw new CantidadInvalidaDeHexagonosONumerosException();
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

    public Hexagono obtenerDesierto(){
        Hexagono desierto = null;
        for(Hexagono hexagono : listaHexagonos){
            if (!hexagono.puedeGenerarRecursos()){
                desierto = hexagono;
            }
        }
        return desierto;
    }


    private void validarVertice(Coordenadas coordenadas, Jugador jugador) {
        if (!this.sonCoordenadasValidas(coordenadas)){
            throw new PosInvalidaParaConstruirException();
        }
        Vertice vertice = mapaVertices.get(coordenadas);
        if (vertice == null){
            throw new PosInvalidaParaConstruirException();
        }
    }

    public ArrayList<Recurso> colocarConstruccionInicial(Construccion construccion, Coordenadas coordenadas,
            Jugador jugador) {
        validarVertice(coordenadas, jugador);
        Vertice vertice = mapaVertices.get(coordenadas);

        if (!construccion.validarConstruccionInicial(vertice, jugador)){
            throw new NoEsPosibleConstruirException();
        }

        return vertice.construirInicial(construccion, jugador);
    }

    public boolean sonCoordenadasValidas(Coordenadas coordenadas) {
        return mapaVertices.containsKey(coordenadas);
    }

    public void colocarEn(Coordenadas coordenadas, Construccion construccion, Jugador jugador) {
        validarVertice(coordenadas, jugador);

        Vertice vertice = mapaVertices.get(coordenadas);
        
        if (!construccion.validarConstruccion(vertice, jugador)){
            throw new NoEsPosibleConstruirException();
        }
        var recursosNecesarios = construccion.obtenerRecursosNecesarios();
        this.cobrar(jugador, recursosNecesarios);
        vertice.construir(construccion, jugador);
    }

    public void producirRecurso(int produccionDado){
        for(Vertice vertice : mapaVertices.values()){
            if (vertice.tieneConstruccion()){
                ArrayList<Recurso> recursos = vertice.producirRecursos(produccionDado);

                if(!recursos.isEmpty()){
                    Jugador dueno = vertice.obtenerDueno();
                    for(Recurso recurso : recursos){
                        dueno.agregarRecurso(recurso);
                    }
                }
            }
        }
    }

    public ArrayList<Jugador> obtenerJugadoresAdyacentes(Coordenadas coordenadasHexagono) {
        ArrayList<Jugador> jugadores = new ArrayList<>();

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

    public Hexagono obtenerHexagono(Coordenadas coordenadas) {
        return mapaHexagonos.get(coordenadas);
    }

    public boolean estaConstruidoCon(Construccion construccion, Coordenadas coordenadas, Jugador jugador) {
        Vertice vertice = mapaVertices.get(coordenadas);
        return vertice.estaConstruidoCon(construccion, jugador);
    }

    private void validarVerticesCarretera(Coordenadas coordenadaExtremo1, Coordenadas coordenadaExtremo2, Jugador jugador) {
        if (!this.sonCoordenadasValidas(coordenadaExtremo1) || !this.sonCoordenadasValidas(coordenadaExtremo2)) {
            throw new PosInvalidaParaConstruirException();
        }
        Vertice vertice1 = mapaVertices.get(coordenadaExtremo1);
        Vertice vertice2 = mapaVertices.get(coordenadaExtremo2);

        if (!vertice1.esAdyacente(vertice2)) {
            throw new PosInvalidaParaConstruirException();
        }
    }

    public void construirCarretera(Coordenadas coordenadaExtremo1, Coordenadas coordenadaExtremo2, Jugador jugador) {
        this.validarVerticesCarretera(coordenadaExtremo1, coordenadaExtremo2, jugador);

        Vertice vertice1 = mapaVertices.get(coordenadaExtremo1);
        Vertice vertice2 = mapaVertices.get(coordenadaExtremo2);
        
        Carretera carretera = new Carretera();
        var recursosNecesarios = carretera.obtenerRecursosNecesarios();
        
        if (!carretera.validarConstruccion(vertice1, jugador) && !carretera.validarConstruccion(vertice2, jugador)){
            throw new NoEsPosibleConstruirException();
        }
               
        this.cobrar(jugador, recursosNecesarios);
        this.construirCarreteraGeneral(carretera, vertice1, vertice2, jugador);

    }

    private void construirCarreteraGeneral(Carretera carretera, Vertice vertice1, Vertice vertice2, Jugador jugador){
        vertice1.construirCarretera(carretera, jugador);
        vertice2.construirCarretera(carretera, jugador);
        jugador.agregarConstruccion(carretera);
        this.listaCarreteras.add(carretera);
    }

    private void cobrar(Jugador jugador, ArrayList<Recurso> recursos){
        for (Recurso recurso : recursos){
            jugador.removerRecurso(recurso);
        }
    }

    public ArrayList<Banca> obtenerBancasDisponibles(Jugador jugador) {
        ArrayList<Banca> bancasDisponibles = new ArrayList<>();
        for (Vertice vertice : mapaVertices.values()) {
            if (vertice.tieneConstruccion() && vertice.esDueno(jugador)) {
                ArrayList<Banca> disponibles = vertice.obtenerBancasDisponibles();
                bancasDisponibles.addAll(disponibles);
            }
        }
        bancasDisponibles.add(new Banca4a1());
        return bancasDisponibles;
    }

    public void construirCarreteraGratis(Coordenadas coordenadaExtremo1, Coordenadas coordenadaExtremo2, Jugador jugador) {
        this.validarVerticesCarretera(coordenadaExtremo1, coordenadaExtremo2, jugador);

        Vertice vertice1 = mapaVertices.get(coordenadaExtremo1);
        Vertice vertice2 = mapaVertices.get(coordenadaExtremo2);
        
        Carretera carretera = new Carretera();
        var recursosNecesarios = carretera.obtenerRecursosNecesarios();
        
        if (!carretera.validarConstruccionInicial(vertice1, jugador) && !carretera.validarConstruccionInicial(vertice2, jugador)) {
            throw new NoEsPosibleConstruirException();
        }
        
        this.construirCarreteraGeneral(carretera, vertice1, vertice2, jugador);
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
                mejor = Math.max(mejor, 1);
                continue;
            }

            if (visitadosComponentes.contains(v1) || visitadosComponentes.contains(v2)) {
                continue;
            }

            Set<Vertice> visitados = new HashSet<>();
            Vertice extremo1 = dfsBuscarExtremo(v1, jugador, visitados);
            Vertice extremo2 = dfsBuscarExtremo(v2, jugador, visitados);

            visitadosComponentes.addAll(visitados);

            mejor = Math.max(mejor, longitudDesdeExtremo(extremo1, v1, jugador));
            mejor = Math.max(mejor, longitudDesdeExtremo(extremo2, v2, jugador));
        }
        return mejor;
    }
    private int longitudDesdeExtremo(Vertice extremo, Vertice extremoA, Jugador jugador){
        Vertice inicio = (extremo == null) ? extremoA : extremo;
        return dfsLongitud(inicio, null, jugador);
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
        if (listaHexagonos.size() != otro.listaHexagonos.size()
                || listaNumeros.size() != otro.listaNumeros.size()) {
            return false;
        }
        for (int i = 0; i < listaHexagonos.size(); i++) {
            Hexagono hex1 = listaHexagonos.get(i);
            Hexagono hex2 = otro.listaHexagonos.get(i);
            if (!hex1.equals(hex2)) {
                return false;
            }
        }
        for (int i = 0; i < listaNumeros.size(); i++) {
            Produccion num1 = listaNumeros.get(i);
            Produccion num2 = otro.listaNumeros.get(i);
            if (!num1.equals(num2)) {
                return false;
            }
        }
        return true;
    }
}
