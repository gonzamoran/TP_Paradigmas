package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.GestorDeTurnos;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaConstruirPoblado extends VentanaModalBase {
    private final GestorDeTurnos gestor;
    private final Coordenadas[] seleccion = { null };
    private Label lblSeleccion;

    public VentanaConstruirPoblado(Stage stage, String nombreJugador, GestorDeTurnos gestor, TableroUI tableroUI) {
        super(stage, "Construir Poblado", nombreJugador, tableroUI);
        this.gestor = gestor;
        construirYMostrar();
    }

    @Override
    protected String getTitulo() {
        return "Construcción de Poblado";
    }

    @Override
    protected String getInstruccion() {
        return "Haz clic en un vértice del tablero para colocar el poblado";
    }

    @Override
    protected VBox crearContenidoPersonalizado() {
        lblSeleccion = new Label("Ningún vértice seleccionado");
        lblSeleccion.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");

        if (tableroUI != null) {
            tableroUI.habilitarSeleccionVertice(coord -> {
                seleccion[0] = coord;
                lblSeleccion.setText("Vértice seleccionado: (" + coord.obtenerCoordenadaX() + ", " + coord.obtenerCoordenadaY() + ")");
                lblSeleccion.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
            });
        }

        VBox contenido = new VBox(lblSeleccion);
        return contenido;
    }

    @Override
    protected void alConfirmar() {
        if (seleccion[0] == null) {
            mostrarError("Debes elegir un vértice para el poblado.");
            return;
        }
        ocultarError();
        limpiarSelecciones();
        gestor.construir(seleccion[0], new Poblado());
        stage.close();
    }
}