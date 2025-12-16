package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.GestorDeTurnos;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaConstruirCarretera extends VentanaModalBase {
    private final GestorDeTurnos gestor;
    private final Coordenadas[] origen = { null };
    private final Coordenadas[] destino = { null };
    private Label lblOrigen;
    private Label lblDestino;

    public VentanaConstruirCarretera(Stage stage, String nombreJugador, GestorDeTurnos gestor, TableroUI tableroUI) {
        super(stage, "Construir Carretera", nombreJugador, tableroUI);
        this.gestor = gestor;
        construirYMostrar();
    }

    @Override
    protected String getTitulo() {
        return "Construcción de Carretera";
    }

    @Override
    protected String getInstruccion() {
        return "Selecciona ORIGEN y DESTINO haciendo clic en dos VÉRTICES del tablero";
    }

    @Override
    protected VBox crearContenidoPersonalizado() {
        lblOrigen = new Label("Origen: ninguno");
        lblDestino = new Label("Destino: ninguno");
        lblOrigen.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        lblDestino.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");

        if (tableroUI != null) {
            tableroUI.deshabilitarSeleccionHexagono();
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

        VBox contenido = new VBox(8, lblOrigen, lblDestino);
        return contenido;
    }

    @Override
    protected void alConfirmar() {
        if (origen[0] == null || destino[0] == null) {
            mostrarError("Debes elegir dos vértices para la carretera.");
            return;
        }
        ocultarError();
        limpiarSelecciones();
        gestor.construirCarretera(origen[0], destino[0]);
        stage.close();
    }
}