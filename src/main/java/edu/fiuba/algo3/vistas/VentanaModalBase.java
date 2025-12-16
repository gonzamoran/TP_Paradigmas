package edu.fiuba.algo3.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public abstract class VentanaModalBase {
    
    protected final Stage stage;
    protected final String nombreJugador;
    protected final TableroUI tableroUI;
    protected Label lblError;
    protected boolean mostrarBotonCancelar = true;
    
    public VentanaModalBase(Stage stage, String titulo, String nombreJugador, TableroUI tableroUI) {
        this.stage = stage;
        this.nombreJugador = nombreJugador;
        this.tableroUI = tableroUI;
        
        configurarStage(titulo);
    }
    
    private void configurarStage(String titulo) {
        stage.setTitle(titulo);
        if (tableroUI != null && tableroUI.getScene() != null && tableroUI.getScene().getWindow() instanceof Stage owner) {
            stage.initOwner(owner);
        }
        stage.setAlwaysOnTop(true);
    }
    
    protected final void construirYMostrar() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: white; -fx-min-width: 380;");
        
        Label lblTitulo = new Label(getTitulo());
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label lblJugador = new Label("Jugador: " + nombreJugador);
        lblJugador.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        
        Label lblInstruccion = new Label(getInstruccion());
        lblInstruccion.setStyle("-fx-font-size: 12px;");
        lblInstruccion.setWrapText(true);
        
        lblError = new Label();
        lblError.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        lblError.setVisible(false);
        
        VBox contenidoPersonalizado = crearContenidoPersonalizado();
        
        HBox botonera = crearBotonera();
        
        root.getChildren().addAll(lblTitulo, lblJugador, lblInstruccion, lblError, contenidoPersonalizado, botonera);
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> alCerrar());
        
        if (esModal()) {
            stage.showAndWait();
        } else {
            stage.show();
        }
    }
    
    private HBox crearBotonera() {
        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 16;");
        btnConfirmar.setOnAction(e -> alConfirmar());
        
        HBox botonera = new HBox(10);
        botonera.getChildren().add(btnConfirmar);
        
        if (mostrarBotonCancelar) {
            Button btnCancelar = new Button("Cancelar");
            btnCancelar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 8 16;");
            btnCancelar.setOnAction(e -> alCancelar());
            botonera.getChildren().add(btnCancelar);
        }
        
        botonera.setAlignment(Pos.CENTER);
        return botonera;
    }
    
    protected abstract String getTitulo();
    protected abstract String getInstruccion();
    protected abstract VBox crearContenidoPersonalizado();
    protected abstract void alConfirmar();
    
    protected void alCancelar() {
        limpiarSelecciones();
        stage.close();
    }
    
    protected void alCerrar() {
        limpiarSelecciones();
    }
    
    protected void limpiarSelecciones() {
        if (tableroUI != null) {
            tableroUI.deshabilitarSeleccionVertice();
            tableroUI.deshabilitarSeleccionHexagono();
        }
    }
    
    protected void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setVisible(true);
    }
    
    protected void ocultarError() {
        lblError.setVisible(false);
    }
    
    protected boolean esModal() {
        return false;
    }
}
