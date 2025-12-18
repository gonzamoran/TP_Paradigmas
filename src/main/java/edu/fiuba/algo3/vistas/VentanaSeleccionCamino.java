package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.function.Consumer;
import javafx.application.Platform;

public class VentanaSeleccionCamino extends VentanaModalBase {
    private final Consumer<java.util.List<Coordenadas>> onSelected;
    private final Coordenadas[] origen = { null };
    private final Coordenadas[] destino = { null };
    private Label lblOrigen;
    private Label lblDestino;

    private final boolean bloquearCierre;
    private final boolean deshabilitarAlCerrar;

    public VentanaSeleccionCamino(Stage stage, String nombreJugador, TableroUI tableroUI, Consumer<java.util.List<Coordenadas>> onSelected) {
        this(stage, nombreJugador, tableroUI, onSelected, true, true);
    }

    public VentanaSeleccionCamino(Stage stage, String nombreJugador, TableroUI tableroUI, Consumer<java.util.List<Coordenadas>> onSelected, boolean bloquearCierre, boolean deshabilitarAlCerrar) {
        super(stage, "Seleccionar Camino", nombreJugador, tableroUI);
        stage.setAlwaysOnTop(true);
        this.onSelected = onSelected;
        this.bloquearCierre = bloquearCierre;
        this.deshabilitarAlCerrar = deshabilitarAlCerrar;
        this.mostrarBotonCancelar = false;
        if (bloquearCierre) {
            stage.setOnCloseRequest(e -> {
                e.consume();
            });
        } else {
            stage.setOnCloseRequest(e -> {
                if (deshabilitarAlCerrar && tableroUI != null) tableroUI.deshabilitarSeleccionVertice();
            });
        }
        construirYMostrar();
    }

    @Override
    protected String getTitulo() {
        return "Seleccionar Camino";
    }

    @Override
    protected String getInstruccion() {
        return "Seleccioná ORIGEN y DESTINO haciendo clic en dos VÉRTICES del tablero";
    }

    @Override
    protected VBox crearContenidoPersonalizado() {
        lblOrigen = new Label("Origen: ninguno");
        lblDestino = new Label("Destino: ninguno");
        lblOrigen.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        lblDestino.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        configurarSeleccion();

        VBox contenido = new VBox(8, lblOrigen, lblDestino);
        contenido.setAlignment(Pos.CENTER);
        return contenido;
    }

    private void configurarSeleccion() {
        if (tableroUI != null) {
            tableroUI.habilitarSeleccionVertice(coord -> {
                if (origen[0] == null) {
                    origen[0] = coord;
                    lblOrigen.setText("Origen: (" + coord.obtenerCoordenadaX() + ", " + coord.obtenerCoordenadaY() + ")");
                    lblOrigen.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
                } else if (destino[0] == null) {
                    destino[0] = coord;
                    lblDestino.setText("Destino: (" + coord.obtenerCoordenadaX() + ", " + coord.obtenerCoordenadaY() + ")");
                    lblDestino.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
                } else {
                    if (tableroUI != null) tableroUI.resetearResaltadoVertices();
                    origen[0] = coord;
                    destino[0] = null;
                    lblOrigen.setText("Origen: (" + coord.obtenerCoordenadaX() + ", " + coord.obtenerCoordenadaY() + ")");
                    lblDestino.setText("Destino: ninguno");
                    lblOrigen.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
                    lblDestino.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
                    if (tableroUI != null) tableroUI.resaltarVertice(coord);
                }
            });
        }
    }

    @Override
    protected void alConfirmar() {
        if (origen[0] == null || destino[0] == null) {
            mostrarError("Debes elegir dos vértices para el camino.");
            return;
        }
        ocultarError();
        super.limpiarSelecciones();
        java.util.List<Coordenadas> camino = new java.util.ArrayList<>();
        camino.add(origen[0]);
        camino.add(destino[0]);
        if (onSelected != null) onSelected.accept(camino);
        confirmado = true;
        stage.close();
    }

    @Override
    protected void limpiarSelecciones() {
        if (deshabilitarAlCerrar && tableroUI != null) tableroUI.deshabilitarSeleccionVertice();
    }
}
