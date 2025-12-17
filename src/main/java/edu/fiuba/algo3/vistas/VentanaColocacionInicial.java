package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.function.BiConsumer;

public class VentanaColocacionInicial extends VentanaModalBase {
    private final BiConsumer<Coordenadas, Coordenadas> onConfirm;
    private final Coordenadas[] poblado = { null };
    private final Coordenadas[] extremoCarretera = { null };
    private Label lblPoblado;
    private Label lblCarretera;

    public VentanaColocacionInicial(Stage stage, String nombreJugador, TableroUI tableroUI,
                                    BiConsumer<Coordenadas, Coordenadas> onConfirm) {
        super(stage, "Colocación Inicial", nombreJugador, tableroUI);
        this.onConfirm = onConfirm;
        this.mostrarBotonCancelar = false;
        construirYMostrar();
    }

    @Override
    protected String getTitulo() {
        return "Colocación Inicial";
    }

    @Override
    protected String getInstruccion() {
        return "Selecciona un VÉRTICE para el Poblado y luego otro VÉRTICE ADYACENTE para la Carretera";
    }

    @Override
    protected VBox crearContenidoPersonalizado() {
        lblPoblado = new Label("Poblado: ninguno");
        lblCarretera = new Label("Carretera hacia: ninguno");
        lblPoblado.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        lblCarretera.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");

        configurarSeleccion();

        VBox contenido = new VBox(8, lblPoblado, lblCarretera);
        return contenido;
    }

    private void configurarSeleccion() {
        if (tableroUI != null) {
            tableroUI.deshabilitarSeleccionHexagono();
            tableroUI.habilitarSeleccionVertice(coord -> {
                if (poblado[0] == null) {
                    poblado[0] = coord;
                    lblPoblado.setText("Poblado: (" + coord.obtenerCoordenadaX() + ", " + coord.obtenerCoordenadaY() + ")");
                    lblPoblado.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
                } else if (extremoCarretera[0] == null) {
                    extremoCarretera[0] = coord;
                    lblCarretera.setText("Carretera hacia: (" + coord.obtenerCoordenadaX() + ", " + coord.obtenerCoordenadaY() + ")");
                    lblCarretera.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
                } else {
                    if (tableroUI != null) tableroUI.resetearResaltadoVertices();
                    poblado[0] = coord;
                    extremoCarretera[0] = null;
                    lblPoblado.setText("Poblado: (" + coord.obtenerCoordenadaX() + ", " + coord.obtenerCoordenadaY() + ")");
                    lblCarretera.setText("Carretera hacia: ninguno");
                    lblPoblado.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
                    lblCarretera.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
                    if (tableroUI != null) tableroUI.resaltarVertice(coord);
                }
            });
        }
    }

    @Override
    protected void alConfirmar() {
        if (poblado[0] == null || extremoCarretera[0] == null) {
            mostrarError("Debes elegir el vértice para el poblado y el vértice adyacente para la carretera.");
            return;
        }
        ocultarError();
        
        limpiarSelecciones();
        
        try {
            if (onConfirm != null) onConfirm.accept(poblado[0], extremoCarretera[0]);
            
            confirmado = true;
            stage.close();
        } catch (RuntimeException ex) {
            configurarSeleccion();
            
            mostrarError(ex.getMessage() != null ? ex.getMessage() : "Error al colocar construcciones iniciales. Intenta de nuevo.");
            resetearSelecciones();
        }
    }

    private void resetearSelecciones() {
        poblado[0] = null;
        extremoCarretera[0] = null;
        lblPoblado.setText("Poblado: ninguno");
        lblCarretera.setText("Carretera hacia: ninguno");
        lblPoblado.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        lblCarretera.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        if (tableroUI != null) {
            tableroUI.resetearResaltadoVertices();
        }
    }

    @Override
    protected boolean esModal() {
        return false;
    }
}
