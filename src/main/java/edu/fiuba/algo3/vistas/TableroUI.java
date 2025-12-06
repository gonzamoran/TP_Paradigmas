package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TableroUI extends Pane {
    
    private static final double RADIO = 50;
    
    private final ArrayList<Hexagono> hexagones;
    private final ArrayList<Produccion> producciones;
    private final Map<Coordenadas, HexagonoUI> hexagonosUI;
    private final Pane contenedorHexagonos;
    
    public TableroUI(ArrayList<Hexagono> hexagones, ArrayList<Produccion> producciones) {
        this.hexagones = hexagones;
        this.producciones = producciones;
        this.hexagonosUI = new HashMap<>();
        this.contenedorHexagonos = new Pane();
        
        this.setStyle("-fx-background-color: #87CEEB;");
        this.setPrefSize(700, 700);
        this.getChildren().add(contenedorHexagonos);
        
        inicializarHexagonos();
        this.layoutChildren();
        centralizarTablero();
    }
    
    private void inicializarHexagonos() {
        int[] cantPorFila = { 3, 4, 5, 4, 3 };
        int[] inicioPorFila = { 2, 1, 0, 1, 2 };

        double distanciaHorizontal = RADIO * Math.sqrt(3);
        double distanciaVertical = RADIO * 1.5;

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
    }
    
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        centralizarTablero();
    }
    
    public void actualizarVista() {
    }
    
    public HexagonoUI getHexagonoUI(Coordenadas coordenadas) {
        return hexagonosUI.get(coordenadas);
    }
}


