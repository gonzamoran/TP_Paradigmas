package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tiposBanca.Banca4a1;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TableroUI extends Pane {
    
    private final ArrayList<Hexagono> hexagones;
    private final ArrayList<Produccion> producciones;
    private final Tablero tablero;
    private final Map<Coordenadas, HexagonoUI> hexagonosUI;
    private final Pane contenedorHexagonos;
    private final Pane contenedorVertices;
    private final Pane contenedorCarreteras;
    private final Pane contenedorOverlay;
    private final Pane contenedorPuertos;
    private final Pane contenedorConexionesPuertos;
    private final Map<Coordenadas, VerticeUI> verticesUI = new HashMap<>();
    private final Map<String, CarreteraUI> carreterasUI = new HashMap<>();
    private Coordenadas ladronActual;

    public TableroUI(ArrayList<Hexagono> hexagones, ArrayList<Produccion> producciones, Tablero tablero) {
        this.hexagones = hexagones;
        this.producciones = producciones;
        this.tablero = tablero;
        this.hexagonosUI = new HashMap<>();
        this.contenedorHexagonos = new Pane();
        this.contenedorVertices = new Pane();
        this.contenedorCarreteras = new Pane();
        this.contenedorOverlay = new Pane();
        this.contenedorPuertos = new Pane();
        this.contenedorConexionesPuertos = new Pane();
        contenedorVertices.setPickOnBounds(false);
        contenedorVertices.setMouseTransparent(false);
        contenedorCarreteras.setPickOnBounds(false);
        contenedorCarreteras.setMouseTransparent(true);
        contenedorOverlay.setPickOnBounds(false);
        contenedorOverlay.setMouseTransparent(true);
        contenedorPuertos.setPickOnBounds(false);
        contenedorPuertos.setMouseTransparent(true);
        contenedorConexionesPuertos.setPickOnBounds(false);
        contenedorConexionesPuertos.setMouseTransparent(true);
        
        this.setStyle("-fx-background-color: #87CEEB;");
        this.setPrefSize(700, 700);
        this.getChildren().addAll(contenedorConexionesPuertos, contenedorHexagonos, contenedorCarreteras, contenedorVertices, contenedorPuertos, contenedorOverlay);
        
        inicializarHexagonos();
        inicializarVertices();
        inicializarPuertos();
        inicializarCarreteras();
        configurarOverlayHexagonos();
        contenedorHexagonos.boundsInLocalProperty().addListener((obs, oldBounds, newBounds) -> centralizarTablero());
        widthProperty().addListener((obs, oldW, newW) -> centralizarTablero());
        heightProperty().addListener((obs, oldH, newH) -> centralizarTablero());
        centralizarTablero();
    }
    
    private void inicializarHexagonos() {
        double radioHexagono = HexagonoUI.getRadio();
        int[] cantPorFila = { 3, 4, 5, 4, 3 };
        int[] inicioPorFila = { 2, 1, 0, 1, 2 };

        double distanciaHorizontal = radioHexagono * Math.sqrt(3);
        double distanciaVertical = radioHexagono * 1.5;

        int indiceHexagono = 0;
        int indiceProduccion = 0;
        
        for (int fila = 0; fila < cantPorFila.length; fila++) {
            int cantidad = cantPorFila[fila];
            int inicio = inicioPorFila[fila];

            for (int j = 0; j < cantidad; j++) {
                int col = inicio + j * 2;
                double x = col * (distanciaHorizontal / 2);
                double y = fila * distanciaVertical;

                if (indiceHexagono < hexagones.size()) {
                    Hexagono hexagono = hexagones.get(indiceHexagono);
                    Coordenadas coord = new Coordenadas(fila, col);
                    
                    HexagonoUI hexUI = new HexagonoUI(x, y, hexagono);
                    
                    if (!hexagono.esDesierto() && indiceProduccion < producciones.size()) {
                        hexUI.setNumeroProduccion(producciones.get(indiceProduccion).getNumero());
                        indiceProduccion++;
                    }
                    
                    contenedorHexagonos.getChildren().add(hexUI);
                    hexagonosUI.put(coord, hexUI);
                    
                    indiceHexagono++;
                }
            }
        }
    }

    private void inicializarVertices() {
        Map<Coordenadas, Integer> marcasPrevias = new HashMap<>();
        Map<Coordenadas, Boolean> ciudadesPrevias = new HashMap<>();
        for (Map.Entry<Coordenadas, VerticeUI> e : verticesUI.entrySet()) {
            VerticeUI v = e.getValue();
            if (v.tieneConstruccion()) {
                marcasPrevias.put(e.getKey(), v.obtenerIndiceJugadorConstruccion());
                ciudadesPrevias.put(e.getKey(), v.esCiudad());
            }
        }

        contenedorVertices.getChildren().clear();
        verticesUI.clear();
        
        double radio = HexagonoUI.getRadio();

        for (Map.Entry<Coordenadas, HexagonoUI> entry : hexagonosUI.entrySet()) {
            Coordenadas hexCoord = entry.getKey();
            HexagonoUI hexUI = entry.getValue();

            double centroX = hexUI.getLayoutX();
            double centroY = hexUI.getLayoutY();

            for (int i = 0; i < 6; i++) {
                double angulo = Math.PI / 3 * i + Math.PI / 2;
                double vx = centroX + radio * Math.cos(angulo);
                double vy = centroY + radio * Math.sin(angulo);

                int indiceLogico = (9 - i) % 6;

                Coordenadas vCoord = calcularCoordenadasVertice(hexCoord, indiceLogico);

                if (tablero.sonCoordenadasValidas(vCoord)) {
                    if (!verticesUI.containsKey(vCoord)) {
                        VerticeUI vertice = new VerticeUI(vx, vy);
                        vertice.setVisible(true);
                        vertice.setMouseTransparent(false);

                        if (marcasPrevias.containsKey(vCoord)) {
                            Integer idx = marcasPrevias.get(vCoord);
                            boolean ciudad = ciudadesPrevias.getOrDefault(vCoord, false);
                            if (idx != null) {
                                if (ciudad) vertice.marcarCiudad(idx);
                                else vertice.marcarConstruccion(idx);
                            }
                        }

                        verticesUI.put(vCoord, vertice);
                        contenedorVertices.getChildren().add(vertice);
                    }
                }
            }
        }
    }

    private void inicializarPuertos() {
        contenedorPuertos.getChildren().clear();
        contenedorConexionesPuertos.getChildren().clear();

        Map<Object, java.util.List<Coordenadas>> grupos = new HashMap<>();
        for (Map.Entry<Coordenadas, VerticeUI> entry : verticesUI.entrySet()) {
            Coordenadas coord = entry.getKey();
            var bancas = tablero.obtenerBancasEn(coord);
            if (bancas == null) continue;
            for (Object banca : bancas) {
                if (banca == null) continue;
                if (banca instanceof Banca4a1) continue;
                grupos.computeIfAbsent(banca, k -> new ArrayList<>()).add(coord);
            }
        }


        Bounds bounds = contenedorHexagonos.getBoundsInLocal();
        double cx = bounds.getCenterX();
        double cy = bounds.getCenterY();

        double offsetExterior = 36.0;

        for (Map.Entry<Object, java.util.List<Coordenadas>> e : grupos.entrySet()) {
            var coords = e.getValue();
            if (coords.size() < 2) continue;
            Coordenadas c1 = coords.get(0);
            Coordenadas c2 = coords.get(1);
            VerticeUI v1 = verticesUI.get(c1);
            VerticeUI v2 = verticesUI.get(c2);
            if (v1 == null || v2 == null) continue;

            double x1 = v1.getTranslateX();
            double y1 = v1.getTranslateY();
            double x2 = v2.getTranslateX();
            double y2 = v2.getTranslateY();

            double mx = (x1 + x2) / 2.0;
            double my = (y1 + y2) / 2.0;

            double rx = mx - cx;
            double ry = my - cy;
            double rlen = Math.sqrt(rx * rx + ry * ry);
            if (rlen == 0) rlen = 1;
            rx /= rlen;
            ry /= rlen;

            double ex = x2 - x1;
            double ey = y2 - y1;
            double elen = Math.sqrt(ex * ex + ey * ey);
            if (elen == 0) elen = 1;
            ex /= elen;
            ey /= elen;

            double nx1 = -ey;
            double ny1 = ex;
            double nx2 = ey;
            double ny2 = -ex;

            double dot1 = nx1 * rx + ny1 * ry;
            double dot2 = nx2 * rx + ny2 * ry;
            double nx, ny;
            if (dot1 >= dot2) {
                nx = nx1; ny = ny1;
            } else {
                nx = nx2; ny = ny2;
            }

            double px = mx + nx * offsetExterior;
            double py = my + ny * offsetExterior;

            Line l1 = new Line(px, py, x1, y1);
            Line l2 = new Line(px, py, x2, y2);
            l1.setStrokeWidth(6.0);
            l2.setStrokeWidth(6.0);
            l1.setStroke(javafx.scene.paint.Color.web("#8B5A2B"));
            l2.setStroke(javafx.scene.paint.Color.web("#8B5A2B"));
            l1.setStrokeLineCap(StrokeLineCap.ROUND);
            l2.setStrokeLineCap(StrokeLineCap.ROUND);
            l1.setMouseTransparent(true);
            l2.setMouseTransparent(true);
            contenedorConexionesPuertos.getChildren().addAll(l1, l2);

            PuertoUI puerto = new PuertoUI(px, py, e.getKey());
            contenedorPuertos.getChildren().add(puerto);
        }
    }
    private Coordenadas calcularCoordenadasVertice(Coordenadas hexCoord, int indiceVertice) {

        int r = hexCoord.obtenerCoordenadaX();
        int c = hexCoord.obtenerCoordenadaY();
        
        switch (indiceVertice) {
            case 0: return new Coordenadas(r, c);
            case 1: return new Coordenadas(r, c - 1);
            case 2: return new Coordenadas(r + 1, c - 1);
            case 3: return new Coordenadas(r + 1, c);
            case 4: return new Coordenadas(r + 1, c + 1);
            case 5: return new Coordenadas(r, c + 1);
            default: return new Coordenadas(r, c);
        }
    }
    
    private void centralizarTablero() {
        Bounds bounds = contenedorHexagonos.getBoundsInLocal();
        double centroPaneX = this.getWidth() / 2;
        double centroPaneY = this.getHeight() / 2;
        double centroContenedorX = bounds.getCenterX();
        double centroContenedorY = bounds.getCenterY();
        double offsetX = centroPaneX - centroContenedorX;
        double offsetY = centroPaneY - centroContenedorY;
        contenedorHexagonos.setTranslateX(offsetX);
        contenedorHexagonos.setTranslateY(offsetY);
        contenedorVertices.setTranslateX(offsetX);
        contenedorVertices.setTranslateY(offsetY);
        contenedorCarreteras.setTranslateX(offsetX);
        contenedorCarreteras.setTranslateY(offsetY);
        contenedorOverlay.setTranslateX(offsetX);
        contenedorOverlay.setTranslateY(offsetY);
        contenedorPuertos.setTranslateX(offsetX);
        contenedorPuertos.setTranslateY(offsetY);
        contenedorConexionesPuertos.setTranslateX(offsetX);
        contenedorConexionesPuertos.setTranslateY(offsetY);
    }
    
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        centralizarTablero();
    }
    

    public Coordenadas buscarCoordenadasPorHexagono(Hexagono hex) {
        for (Map.Entry<Coordenadas, HexagonoUI> e : hexagonosUI.entrySet()) {
            if (e.getValue().getHexagonoModelo() == hex) {
                return e.getKey();
            }
        }
        return null;
    }

    public void actualizarLadronEn(Coordenadas coord) {
        if (ladronActual != null) {
            HexagonoUI prev = hexagonosUI.get(ladronActual);
            if (prev != null) prev.mostrarLadron(false);
        }
        ladronActual = coord;
        if (ladronActual != null) {
            HexagonoUI nuevo = hexagonosUI.get(ladronActual);
            if (nuevo != null) nuevo.mostrarLadron(true);
        }
    }
    
    public void habilitarSeleccionHexagono(java.util.function.Consumer<Coordenadas> onHexagonoSeleccionado) {
        for (HexagonoUI hexUI : hexagonosUI.values()) {
            hexUI.resetSeleccion();
        }
        
        for (Map.Entry<Coordenadas, HexagonoUI> entry : hexagonosUI.entrySet()) {
            Coordenadas coord = entry.getKey();
            HexagonoUI hexUI = entry.getValue();
            
            hexUI.habilitarSeleccion(() -> {
                for (HexagonoUI hex : hexagonosUI.values()) {
                    hex.resetSeleccion();
                }
                hexUI.resaltarSeleccion();
                onHexagonoSeleccionado.accept(coord);
            });
        }
    }
    
    public void deshabilitarSeleccionHexagono() {
        for (HexagonoUI hexUI : hexagonosUI.values()) {
            hexUI.deshabilitarSeleccion();
            hexUI.resetSeleccion();
        }
    }

    public void habilitarSeleccionVertice(Consumer<Coordenadas> onVerticeSeleccionado) {
        for (VerticeUI v : verticesUI.values()) {
            v.resetSeleccion();
        }
        
        contenedorVertices.setMouseTransparent(false);
        for (Map.Entry<Coordenadas, VerticeUI> entry : verticesUI.entrySet()) {
            Coordenadas coord = entry.getKey();
            VerticeUI v = entry.getValue();
            v.setVisible(true);
            v.setMouseTransparent(false);
            v.habilitarSeleccion(() -> onVerticeSeleccionado.accept(coord));
        }
    }

    public void deshabilitarSeleccionVertice() {
        for (VerticeUI v : verticesUI.values()) {
            v.deshabilitarSeleccion();
            v.resetSeleccion();
            v.setVisible(true);
            v.setMouseTransparent(!v.tieneConstruccion());
        }
        contenedorVertices.setMouseTransparent(false);
    }

    public void resetearResaltadoVertices() {
        for (VerticeUI v : verticesUI.values()) {
            v.resetSeleccion();
        }
    }

    public void resetearResaltadoVerticesExcepto(Coordenadas coord) {
        for (Coordenadas c : verticesUI.keySet()) {
            if (!c.equals(coord)) {
                verticesUI.get(c).resetSeleccion();
            }
        }
    }

    public void resaltarVertice(Coordenadas coord) {
        VerticeUI v = verticesUI.get(coord);
        if (v != null) v.resaltarSeleccion();
    }
    

    public void marcarPoblado(Coordenadas coord, int indiceJugador) {
        VerticeUI v = verticesUI.get(coord);
        if (v != null) {
            v.marcarConstruccion(indiceJugador);
        }
    }

    public void marcarCiudad(Coordenadas coord, int indiceJugador) {
        VerticeUI v = verticesUI.get(coord);
        if (v != null) {
            v.marcarCiudad(indiceJugador);
        }
    }


    private void configurarOverlayHexagonos() {
        for (HexagonoUI hexUI : hexagonosUI.values()) {
            hexUI.setOverlayPane(contenedorOverlay);
        }
    }

    private void inicializarCarreteras() {
        Map<String, Integer> marcasCarreterasPrevias = new HashMap<>();
        for (Map.Entry<String, CarreteraUI> e : carreterasUI.entrySet()) {
            CarreteraUI c = e.getValue();
            if (c.tieneConstruccion()) {
                marcasCarreterasPrevias.put(e.getKey(), c.obtenerIndiceJugador());
            }
        }

        carreterasUI.clear();
        contenedorCarreteras.getChildren().clear();
        
        for (Map.Entry<Coordenadas, HexagonoUI> entry : hexagonosUI.entrySet()) {
            Coordenadas hexCoord = entry.getKey();
            
            for (int i = 0; i < 6; i++) {
                int siguiente = (i + 1) % 6;
                
                Coordenadas coord1 = calcularCoordenadasVertice(hexCoord, i);
                Coordenadas coord2 = calcularCoordenadasVertice(hexCoord, siguiente);
                
                if (verticesUI.containsKey(coord1) && verticesUI.containsKey(coord2)) {
                    String llave = crearLlaveCarretera(coord1, coord2);
                    
                    if (!carreterasUI.containsKey(llave)) {
                        VerticeUI verticeUI1 = verticesUI.get(coord1);
                        VerticeUI verticeUI2 = verticesUI.get(coord2);
                        
                        double x1 = verticeUI1.getTranslateX();
                        double y1 = verticeUI1.getTranslateY();
                        double x2 = verticeUI2.getTranslateX();
                        double y2 = verticeUI2.getTranslateY();
                        
                        CarreteraUI carretera = new CarreteraUI(x1, y1, x2, y2);

                        if (marcasCarreterasPrevias.containsKey(llave)) {
                            Integer idx = marcasCarreterasPrevias.get(llave);
                            if (idx != null) carretera.marcarConstruccion(idx);
                        }

                        carreterasUI.put(llave, carretera);
                        contenedorCarreteras.getChildren().add(carretera);
                    }
                }
            }
        }
    }

    private String crearLlaveCarretera(Coordenadas coord1, Coordenadas coord2) {
        String key1 = coord1.obtenerCoordenadaX() + "," + coord1.obtenerCoordenadaY();
        String key2 = coord2.obtenerCoordenadaX() + "," + coord2.obtenerCoordenadaY();
        
        if (key1.compareTo(key2) < 0) {
            return key1 + "|" + key2;
        } else {
            return key2 + "|" + key1;
        }
    }

    public void marcarCarretera(Coordenadas coord1, Coordenadas coord2, int indiceJugador) {
        String llave = crearLlaveCarretera(coord1, coord2);
        CarreteraUI carretera = carreterasUI.get(llave);
        if (carretera != null) {
            carretera.marcarConstruccion(indiceJugador);
        }
    }

    public void refrescarDesdeModelo() {
        javafx.application.Platform.runLater(() -> {
            try {

                this.applyCss();
                this.layout();
                contenedorHexagonos.applyCss();
                contenedorHexagonos.layout();

                inicializarVertices();
                inicializarCarreteras();
                inicializarPuertos();
                configurarOverlayHexagonos();
                centralizarTablero();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}


