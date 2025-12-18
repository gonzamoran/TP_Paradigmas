package edu.fiuba.algo3.vistas.ventanas;

import edu.fiuba.algo3.modelo.GestorDeTurnos;
import edu.fiuba.algo3.modelo.construcciones.Poblado;
import edu.fiuba.algo3.modelo.excepciones.NoEsPosibleConstruirException;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import edu.fiuba.algo3.vistas.TableroUI;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaConstruirPoblado extends VentanaModalBase {
    private final GestorDeTurnos gestor;
    private final Coordenadas[] seleccion = { null };
    private Label lblSeleccion;
    private final Runnable onBuilt;

    public VentanaConstruirPoblado(Stage stage, String nombreJugador, GestorDeTurnos gestor, TableroUI tableroUI, Runnable onBuilt) {
        super(stage, "Construir Poblado", nombreJugador, tableroUI);
        this.gestor = gestor;
        this.onBuilt = onBuilt;
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

        configurarSeleccion();

        VBox contenido = new VBox(lblSeleccion);
        return contenido;
    }

    private void configurarSeleccion() {
        if (tableroUI != null) {
            tableroUI.habilitarSeleccionVertice(coord -> {
                tableroUI.resetearResaltadoVerticesExcepto(coord);
                seleccion[0] = coord;
                lblSeleccion.setText("Vértice seleccionado: (" + coord.obtenerCoordenadaX() + ", " + coord.obtenerCoordenadaY() + ")");
                lblSeleccion.setStyle("-fx-font-size: 12px; -fx-text-fill: #27ae60; -fx-font-weight: bold;");
            });
        }
    }

    @Override
    protected void alConfirmar() {
        if (seleccion[0] == null) {
            mostrarError("Debes elegir un vértice para el poblado.");
            return;
        }
        ocultarError();
        
        limpiarSelecciones();
        
        try {
            gestor.construir(seleccion[0], new Poblado());

            try {
                int indiceJugador = gestor.obtenerIndiceJugadorActual();
                if (tableroUI != null) tableroUI.marcarPoblado(seleccion[0], indiceJugador);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (onBuilt != null) {
                try { onBuilt.run(); } catch (Exception ex) { ex.printStackTrace(); }
            }

            try {
                if (tableroUI != null) tableroUI.refrescarDesdeModelo();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            confirmado = true;
            stage.close();
        } catch (NoEsPosibleConstruirException ex) {
            configurarSeleccion();
            
            mostrarError(ex.getMessage() != null ? ex.getMessage() : "No es posible construir en esta ubicación. Intenta en otro lugar.");
            resetearSeleccion();
        }
    }

    private void resetearSeleccion() {
        seleccion[0] = null;
        lblSeleccion.setText("Ningún vértice seleccionado");
        lblSeleccion.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        if (tableroUI != null) tableroUI.resetearResaltadoVertices();
    }

    @Override
    protected void limpiarSelecciones() {
        if (tableroUI != null) {
            tableroUI.deshabilitarSeleccionVertice();
        }
    }
}