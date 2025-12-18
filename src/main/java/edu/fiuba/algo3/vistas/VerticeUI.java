package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.vistas.ventanas.ColoresJugadores;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class VerticeUI extends Group {
    private boolean seleccionado = false;
    private Integer indiceJugadorConstruccion = null;
    private boolean esCiudad = false;
    private static final Color COLOR_VACIO = Color.web("#ffcc66");
    private static final Color COLOR_BORDE_VACIO = Color.web("#8b4513");
    private final Circle circulo;
    private final Group casaGrupo;

    public VerticeUI(double x, double y) {
        super();
        setTranslateX(x);
        setTranslateY(y);

        circulo = new Circle(0, 0, 8);
        circulo.setFill(COLOR_VACIO);
        circulo.setStroke(COLOR_BORDE_VACIO);
        circulo.setStrokeWidth(2);

        casaGrupo = new Group();
        casaGrupo.setVisible(false);
        
        getChildren().addAll(circulo, casaGrupo);
        setMouseTransparent(false);
    }

    public void habilitarSeleccion(Runnable onSeleccionado) {
        seleccionado = false;
        limpiarResaltado();
        
        setOnMouseClicked(e -> {
            seleccionado = true;
            resaltarSeleccion();
            if (onSeleccionado != null) onSeleccionado.run();
        });

        setOnMouseEntered(e -> {
            if (!seleccionado) {
                circulo.setStrokeWidth(3);
                circulo.setStroke(Color.DODGERBLUE);
                if (indiceJugadorConstruccion != null) {
                    casaGrupo.setOpacity(0.3);
                }
            }
        });

        setOnMouseExited(e -> {
            if (!seleccionado) {
                circulo.setStrokeWidth(2);
                if (indiceJugadorConstruccion == null) {
                    circulo.setStroke(COLOR_BORDE_VACIO);
                } else {
                    circulo.setStroke(ColoresJugadores.obtenerColorBorde(indiceJugadorConstruccion));
                    casaGrupo.setOpacity(1.0);
                }
            }
        });
    }

    public void deshabilitarSeleccion() {
        setOnMouseClicked(null);
        setOnMouseEntered(null);
        setOnMouseExited(null);
        limpiarResaltado();
    }

    public void resaltarSeleccion() {
        circulo.setFill(Color.LIMEGREEN);
        circulo.setStroke(Color.DARKGREEN);
        circulo.setStrokeWidth(3);
        seleccionado = true;
        if (indiceJugadorConstruccion != null) {
            casaGrupo.setOpacity(0.3);
        }
    }

    public void resetSeleccion() {
        seleccionado = false;
        limpiarResaltado();
        if (indiceJugadorConstruccion != null) {
            casaGrupo.setOpacity(1.0);
        }
    }

    private void limpiarResaltado() {
        if (indiceJugadorConstruccion == null) {
            circulo.setFill(COLOR_VACIO);
            circulo.setStroke(COLOR_BORDE_VACIO);
        } else {
            circulo.setFill(ColoresJugadores.obtenerColorPoblado(indiceJugadorConstruccion));
            circulo.setStroke(ColoresJugadores.obtenerColorBorde(indiceJugadorConstruccion));
        }
        circulo.setStrokeWidth(2);
    }
    

    public void marcarConstruccion(int indiceJugador) {
        this.indiceJugadorConstruccion = indiceJugador;
        this.esCiudad = false;
        limpiarResaltado();
        dibujarCasa(indiceJugador);
        casaGrupo.setOpacity(1.0);
        setMouseTransparent(false);
    }
    

    public void desmarcarConstruccion() {
        this.indiceJugadorConstruccion = null;
        this.esCiudad = false;
        limpiarResaltado();
        casaGrupo.getChildren().clear();
        casaGrupo.setVisible(false);
        setMouseTransparent(false);
    }

    public void marcarCiudad(int indiceJugador) {
        this.indiceJugadorConstruccion = indiceJugador;
        this.esCiudad = true;
        limpiarResaltado();
        dibujarCiudad(indiceJugador);
        casaGrupo.setOpacity(1.0);
        setMouseTransparent(false);
    }
    

    public boolean tieneConstruccion() {
        return indiceJugadorConstruccion != null;
    }
    

    public Integer obtenerIndiceJugadorConstruccion() {
        return indiceJugadorConstruccion;
    }

    public boolean esCiudad() {
        return esCiudad;
    }
    

    private void dibujarCasa(int indiceJugador) {
        casaGrupo.getChildren().clear();
        
        Color colorCasa = ColoresJugadores.obtenerColorPoblado(indiceJugador);
        Color colorBorde = ColoresJugadores.obtenerColorBorde(indiceJugador);
        
        Rectangle base = new Rectangle(-7, -3, 14, 10);
        base.setFill(colorCasa);
        base.setStroke(colorBorde);
        base.setStrokeWidth(1.5);
        
        Polygon techo = new Polygon(
            -8, -3,
            0, -12,
            8, -3
        );
        techo.setFill(colorBorde);
        techo.setStroke(colorBorde);
        techo.setStrokeWidth(1.5);
        
        casaGrupo.getChildren().addAll(techo, base);
        casaGrupo.setVisible(true);
    }

    private void dibujarCiudad(int indiceJugador) {
        casaGrupo.getChildren().clear();

        Color colorCasa = ColoresJugadores.obtenerColorPoblado(indiceJugador);
        Color colorBorde = ColoresJugadores.obtenerColorBorde(indiceJugador);
        
        Rectangle chimenea = new Rectangle(2, -18, 4, 10); 
        chimenea.setFill(colorCasa.darker()); 
        chimenea.setStroke(colorBorde);
        chimenea.setStrokeWidth(1);

        Rectangle cuadradoTrasero = new Rectangle(-6, -12, 12, 12);
        cuadradoTrasero.setFill(colorCasa.darker());
        cuadradoTrasero.setStroke(colorBorde);
        cuadradoTrasero.setStrokeWidth(1);


        Rectangle edificioFrente = new Rectangle(-10, -6, 20, 12);
        edificioFrente.setFill(colorCasa);
        edificioFrente.setStroke(colorBorde);
        edificioFrente.setStrokeWidth(1.5);


        Group ventanas = new Group();
        for (int i = 0; i < 3; i++) {
            double xVentana = -7 + (i * 5.5); 
            Rectangle ventana = new Rectangle(xVentana, -3, 3, 4);
            ventana.setFill(Color.web("#FFFFE0"));
            ventana.setStroke(colorBorde);
            ventana.setStrokeWidth(0.5);
            ventanas.getChildren().add(ventana);
        }
        casaGrupo.getChildren().addAll(chimenea, cuadradoTrasero, edificioFrente, ventanas);
        casaGrupo.setVisible(true);
    }
}
