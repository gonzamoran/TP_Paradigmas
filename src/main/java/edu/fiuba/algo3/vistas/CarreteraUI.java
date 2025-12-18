package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.vistas.ventanas.ColoresJugadores;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

public class CarreteraUI extends Group {
    private final Line linea;
    private Integer indiceJugador = null;
    private static final Color COLOR_VACIO = Color.web("#8b7355");
    private static final Color COLOR_CONTORNO = Color.web("#2c1810");
    private static final double STROKE_WIDTH_VACIO = 3.0;
    private static final double STROKE_WIDTH_CONTORNO_VACIO = 5.0;
    private static final double STROKE_WIDTH_CONSTRUCCION = 5.0;
    private static final double STROKE_WIDTH_CONTORNO_CONSTRUCCION = 7.0;

    public CarreteraUI(double x1, double y1, double x2, double y2) {
        super();
        
        Line contorno = new Line(x1, y1, x2, y2);
        contorno.setStroke(COLOR_CONTORNO);
        contorno.setStrokeWidth(STROKE_WIDTH_CONTORNO_VACIO);
        contorno.setStrokeLineCap(StrokeLineCap.ROUND);
        
        linea = new Line(x1, y1, x2, y2);
        linea.setStroke(COLOR_VACIO);
        linea.setStrokeWidth(STROKE_WIDTH_VACIO);
        linea.setStrokeLineCap(StrokeLineCap.ROUND);
        
        getChildren().addAll(contorno, linea);
        setMouseTransparent(true);
    }

    public void marcarConstruccion(int indiceJugador) {
        this.indiceJugador = indiceJugador;
        Color colorJugador = ColoresJugadores.obtenerColorPoblado(indiceJugador);
        
        Line contorno = (Line) getChildren().get(0);
        contorno.setStroke(COLOR_CONTORNO);
        contorno.setStrokeWidth(STROKE_WIDTH_CONTORNO_CONSTRUCCION);
        
        linea.setStroke(colorJugador);
        linea.setStrokeWidth(STROKE_WIDTH_CONSTRUCCION);
    }

    public void desmarcarConstruccion() {
        this.indiceJugador = null;
        
        Line contorno = (Line) getChildren().get(0);
        contorno.setStroke(COLOR_CONTORNO);
        contorno.setStrokeWidth(STROKE_WIDTH_CONTORNO_VACIO);
        
        linea.setStroke(COLOR_VACIO);
        linea.setStrokeWidth(STROKE_WIDTH_VACIO);
    }

    public boolean tieneConstruccion() {
        return indiceJugador != null;
    }

    public Integer obtenerIndiceJugador() {
        return indiceJugador;
    }
}
