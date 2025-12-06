package edu.fiuba.algo3.vistas;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
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
    
    public HexagonoUI(double centroX, double centroY, Hexagono hexagonoModelo) {
        this.centroX = centroX;
        this.centroY = centroY;
        this.hexagonoModelo = hexagonoModelo;

        poligonoHexagono = new Polygon();
        crearFormaHexagonalConPunta();
        aplicarColorSegunTipo();

        fondoNumero = new Circle(centroX, centroY, 18);
        fondoNumero.setFill(Color.web("#F5DEB3"));
        fondoNumero.setStroke(Color.BLACK);
        fondoNumero.setStrokeWidth(1.5);
        fondoNumero.setVisible(false);

        textoProduccion = new Text();
        textoProduccion.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");

        this.getChildren().addAll(poligonoHexagono, fondoNumero, textoProduccion);
    }

    private void crearFormaHexagonalConPunta() {
        for (int i = 0; i < 6; i++) {
            double angulo = Math.PI / 3 * i + Math.PI / 2;
            double x = centroX + RADIO * Math.cos(angulo);
            double y = centroY + RADIO * Math.sin(angulo);
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
            
            javafx.application.Platform.runLater(() -> {
                double anchoTexto = textoProduccion.getLayoutBounds().getWidth();
                double altoTexto = textoProduccion.getLayoutBounds().getHeight();
                textoProduccion.setLayoutX(centroX - anchoTexto / 2);
                textoProduccion.setLayoutY(centroY + altoTexto / 3);
                fondoNumero.setVisible(true);
            });
        }
    }
    
    public void mostrarLadron(boolean mostrar) {
        if (mostrar) {
            poligonoHexagono.setStroke(Color.RED);
            poligonoHexagono.setStrokeWidth(4);
        } else {
            poligonoHexagono.setStroke(Color.BLACK);
            poligonoHexagono.setStrokeWidth(2);
        }
    }
    
    public Hexagono getHexagonoModelo() {
        return hexagonoModelo;
    }
    
    public double getCentroX() {
        return centroX;
    }
    
    public double getCentroY() {
        return centroY;
    }
    
    public static double getRadio() {
        return RADIO;
    }
}


