package edu.fiuba.algo3.vistas;

import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;
import javafx.scene.shape.Polyline;
import javafx.scene.Node;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.tiposHexagono.*;



public class HexagonoUI extends Pane {
    private static final double RADIO = 50;
    
    private final double centroX;
    private final double centroY;
    private final Hexagono hexagonoModelo;
    private final Polygon poligonoHexagono;
    private final Circle fondoNumero;
    private final Text textoProduccion;
    private boolean seleccionado = false;
    private Pane overlayPane;
    private Polygon highlightPolygon;
    private Group selectionOverlay;
    
    public HexagonoUI(double centroX, double centroY, Hexagono hexagonoModelo) {
        this.centroX = centroX;
        this.centroY = centroY;
        this.hexagonoModelo = hexagonoModelo;

        poligonoHexagono = new Polygon();
        crearFormaHexagonalConPunta();
        aplicarColorSegunTipo();

        poligonoHexagono.setPickOnBounds(false);

        fondoNumero = new Circle(0, 0, 18);
        fondoNumero.setFill(Color.web("#F5DEB3"));
        fondoNumero.setStroke(Color.BLACK);
        fondoNumero.setStrokeWidth(1.5);
        fondoNumero.setVisible(false);

        fondoNumero.setMouseTransparent(true);

        textoProduccion = new Text();
        textoProduccion.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");

        textoProduccion.setMouseTransparent(true);

        this.getChildren().addAll(poligonoHexagono, fondoNumero, textoProduccion);

        this.setMouseTransparent(false);
        this.setPickOnBounds(false);

        this.setLayoutX(centroX);
        this.setLayoutY(centroY);
    }

    public void setOverlayPane(Pane overlayPane) {
        this.overlayPane = overlayPane;
    }

    private void crearFormaHexagonalConPunta() {
        poligonoHexagono.getPoints().clear();
        for (int i = 0; i < 6; i++) {
            double angulo = Math.PI / 3 * i + Math.PI / 2;
            double x = RADIO * Math.cos(angulo);
            double y = RADIO * Math.sin(angulo);
            poligonoHexagono.getPoints().addAll(x, y);
        }

        poligonoHexagono.setStroke(Color.BLACK);
        poligonoHexagono.setStrokeWidth(2);
    }

    private void aplicarColorSegunTipo() {
        if (hexagonoModelo == null) {
            poligonoHexagono.setFill(Color.LIGHTGRAY);
            return;
        }
        Color color = Color.web("#CCCCCC");
        if (hexagonoModelo instanceof Bosque) {
            color = Color.web("#2ecc71");
        } else if (hexagonoModelo instanceof Campo) {
            color = Color.web("#f1c40f");
        } else if (hexagonoModelo instanceof Pastizal) {
            color = Color.web("#ecf0f1");
        } else if (hexagonoModelo instanceof Colina) {
            color = Color.web("#e74c3c");
        } else if (hexagonoModelo instanceof Montana) {
            color = Color.web("#95a5a6");
        } else if (hexagonoModelo instanceof Desierto) {
            color = Color.web("#F5DEB3");
        }

        poligonoHexagono.setFill(color);
    }
    
    public void setNumeroProduccion(int numero) {
        if (hexagonoModelo.esDesierto()) {
            textoProduccion.setText("");
            fondoNumero.setVisible(false);
        } else {
            textoProduccion.setText(String.valueOf(numero));
            
            if (numero == 6 || numero == 8) {
                textoProduccion.setFill(Color.RED);
            } else {
                textoProduccion.setFill(Color.BLACK);
            }

            textoProduccion.setTextOrigin(VPos.CENTER);
            fondoNumero.setCenterX(0);
            fondoNumero.setCenterY(0);
            fondoNumero.setVisible(true);

            Platform.runLater(() -> {
                textoProduccion.applyCss();
                double ancho = textoProduccion.getLayoutBounds().getWidth();
                textoProduccion.setLayoutX(-ancho / 2.0);
                textoProduccion.setLayoutY(0);
            });
        }
    }
    
    public void mostrarLadron(boolean mostrar) {
        if (overlayPane == null) {
            poligonoHexagono.setStroke(mostrar ? Color.RED : Color.BLACK);
            poligonoHexagono.setStrokeWidth(mostrar ? 4 : 2);
            return;
        }

        if (mostrar) {
            Group robberOverlay = obtenerOrCrearRobberOverlay();
            robberOverlay.setTranslateX(this.getLayoutX());
            robberOverlay.setTranslateY(this.getLayoutY());
            if (!overlayPane.getChildren().contains(robberOverlay)) {
                overlayPane.getChildren().add(robberOverlay);
            }
        } else {
            String id = generarIdRobberOverlay();
            overlayPane.getChildren().removeIf(n -> id.equals(n.getId()));
        }
    }

    private String generarIdRobberOverlay() {
        return "robber-" + System.identityHashCode(this);
    }

    private Group obtenerOrCrearRobberOverlay() {
        String id = generarIdRobberOverlay();
        for (Node n : overlayPane.getChildren()) {
            if (id.equals(n.getId()) && n instanceof Group g) {
                return g;
            }
        }
        Group g = new Group();
        g.setId(id);

        Circle fondo = new Circle(0, 0, 16);
        fondo.setFill(Color.BLACK);

        Circle cara = new Circle(0, 0, 14);
        cara.setFill(Color.web("#b0b0b0"));
        cara.setStroke(Color.BLACK);
        cara.setStrokeWidth(2);

        Rectangle antifaz = new Rectangle(-12, -6, 24, 10);
        antifaz.setArcWidth(8);
        antifaz.setArcHeight(8);
        antifaz.setFill(Color.BLACK);
        antifaz.setStroke(Color.WHITE);
        antifaz.setStrokeWidth(1.5);

        Circle ojoIzq = new Circle(-6, -1, 2.5);
        ojoIzq.setFill(Color.WHITE);
        ojoIzq.setStroke(Color.BLACK);
        ojoIzq.setStrokeWidth(1);

        Circle ojoDer = new Circle(6, -1, 2.5);
        ojoDer.setFill(Color.WHITE);
        ojoDer.setStroke(Color.BLACK);
        ojoDer.setStrokeWidth(1);

        g.getChildren().addAll(fondo, cara, antifaz, ojoIzq, ojoDer);
        g.setMouseTransparent(true);
        return g;
    }
    
    public void habilitarSeleccion(Runnable onSeleccionado) {
        seleccionado = false;
        resetSeleccion();
        
        poligonoHexagono.setOnMouseClicked(e -> {
            if (onSeleccionado != null) {
                seleccionado = true;
                onSeleccionado.run();
            }
        });
        
        poligonoHexagono.setOnMouseEntered(e -> {
            if (!seleccionado) {
                poligonoHexagono.setStrokeWidth(4);
                poligonoHexagono.setStroke(Color.YELLOW);
            }
            this.setStyle("-fx-cursor: hand;");
            if (overlayPane != null) {
                if (highlightPolygon == null) {
                    highlightPolygon = new Polygon();
                    highlightPolygon.getPoints().setAll(poligonoHexagono.getPoints());
                    highlightPolygon.setFill(Color.TRANSPARENT);
                    highlightPolygon.setStroke(Color.YELLOW);
                    highlightPolygon.setStrokeWidth(5);
                    highlightPolygon.setMouseTransparent(true);
                }
                highlightPolygon.setTranslateX(this.getLayoutX());
                highlightPolygon.setTranslateY(this.getLayoutY());
                if (!overlayPane.getChildren().contains(highlightPolygon)) {
                    overlayPane.getChildren().add(highlightPolygon);
                }
            }
        });
        
        poligonoHexagono.setOnMouseExited(e -> {
            if (!seleccionado) {
                poligonoHexagono.setStrokeWidth(2);
                poligonoHexagono.setStroke(Color.BLACK);
            }
            this.setStyle("-fx-cursor: default;");
            if (overlayPane != null && highlightPolygon != null) {
                overlayPane.getChildren().remove(highlightPolygon);
            }
        });

    }
    
    public void deshabilitarSeleccion() {
        poligonoHexagono.setOnMouseClicked(null);
        poligonoHexagono.setOnMouseEntered(null);
        poligonoHexagono.setOnMouseExited(null);
        resetSeleccion();
    }
    
    public void resaltarSeleccion() {
        seleccionado = true;
        if (overlayPane != null) {
            if (selectionOverlay == null) {
                selectionOverlay = new Group();
                Circle badge = new Circle(0, 0, 18);
                badge.setFill(Color.color(0.1, 0.6, 0.1, 0.75));
                badge.setStroke(Color.DARKGREEN);
                badge.setStrokeWidth(2);

                Polyline tick = new Polyline();
                tick.getPoints().addAll(
                    -6.0, 0.0,
                    -1.5, 6.0,
                    8.0, -6.0
                );
                tick.setStroke(Color.WHITE);
                tick.setStrokeWidth(3.5);
                tick.setStrokeLineCap(StrokeLineCap.ROUND);
                tick.setStrokeLineJoin(StrokeLineJoin.ROUND);
                tick.setFill(Color.TRANSPARENT);

                selectionOverlay.getChildren().addAll(badge, tick);
                selectionOverlay.setMouseTransparent(true);
            }
            selectionOverlay.setTranslateX(this.getLayoutX());
            selectionOverlay.setTranslateY(this.getLayoutY());
            if (!overlayPane.getChildren().contains(selectionOverlay)) {
                overlayPane.getChildren().add(selectionOverlay);
            }
        } else {
            poligonoHexagono.setStrokeWidth(4);
            poligonoHexagono.setStroke(Color.LIME);
        }
    }

    public void resetSeleccion() {
        seleccionado = false;
        poligonoHexagono.setStrokeWidth(2);
        poligonoHexagono.setStroke(Color.BLACK);
        this.setStyle("-fx-cursor: default;");
        if (overlayPane != null) {
            if (highlightPolygon != null) overlayPane.getChildren().remove(highlightPolygon);
            if (selectionOverlay != null) overlayPane.getChildren().remove(selectionOverlay);
        }
    }
    
    public Hexagono getHexagonoModelo() {
        return hexagonoModelo;
    }
    
    public static double getRadio() {
        return RADIO;
    }
}


