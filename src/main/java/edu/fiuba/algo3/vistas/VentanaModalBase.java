package edu.fiuba.algo3.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;


public abstract class VentanaModalBase {
    
    protected final Stage stage;
    protected final String nombreJugador;
    protected final TableroUI tableroUI;
    protected Label lblError;
    protected boolean mostrarBotonCancelar = true;
    protected boolean confirmado = false;
    
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
        VBox root = new VBox(16);
        root.setPadding(new Insets(18));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: white; -fx-max-width: 350; -fx-pref-width: 350;");
        
        Label lblTitulo = new Label(getTitulo());
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-wrap-text: true;");
        lblTitulo.setWrapText(true);
        lblTitulo.setMaxWidth(350);
        
        Label lblJugador = new Label("Jugador: " + nombreJugador);
        lblJugador.setStyle("-fx-font-size: 11px; -fx-text-fill: #7f8c8d;");
        lblJugador.setWrapText(true);
        lblJugador.setMaxWidth(350);
        
        Label lblInstruccion = new Label(getInstruccion());
        lblInstruccion.setStyle("-fx-font-size: 11px;");
        lblInstruccion.setWrapText(true);
        lblInstruccion.setMaxWidth(350);
        
        lblError = new Label();
        lblError.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 10px;");
        lblError.setWrapText(true);
        lblError.setMaxWidth(350);
        lblError.setVisible(false);
        
        VBox contenidoPersonalizado = crearContenidoPersonalizado();
        
        VBox botonera = crearBotonera();
        
        root.getChildren().addAll(lblTitulo, lblJugador, lblInstruccion, lblError, contenidoPersonalizado, botonera);
        
        root.setMinHeight(300);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setOnHidden(e -> alCerrar());

        if (esModal()) {
            stage.setOnShown(ev -> {
                stage.sizeToScene();
                posicionarFueraDelTablero();
            });
            stage.showAndWait();
        } else {
            stage.setOnShown(ev -> {
                stage.sizeToScene();
                posicionarFueraDelTablero();
            });
            stage.show();
        }
    }

    private void posicionarFueraDelTablero() {
        if (tableroUI == null || tableroUI.getScene() == null || !(tableroUI.getScene().getWindow() instanceof Stage owner)) {
            return;
        }
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();
        double margin = 16;

        Bounds tableroPantalla = tableroUI.localToScreen(tableroUI.getBoundsInLocal());
        if (tableroPantalla != null) {
            double tableroLeft = tableroPantalla.getMinX();
            
            double targetX = tableroLeft - stageWidth - margin;
            targetX = screen.getMinX() + 200;
            

            double targetY = tableroPantalla.getMinY();
            if (targetY + stageHeight > screen.getMaxY() - margin) {
                targetY = screen.getMaxY() - stageHeight - margin;
            }
            if (targetY < screen.getMinY() + margin) {
                targetY = screen.getMinY() + margin;
            }

            stage.setX(targetX);
            stage.setY(targetY);
            return;
        }

        double ownerX = owner.getX();
        double ownerY = owner.getY();
        double ownerWidth = owner.getWidth();
        double ownerHeight = owner.getHeight();

        double targetX = ownerX - stageWidth - margin;
        if (targetX < screen.getMinX()) {
            targetX = ownerX + ownerWidth + margin;
        }
        if (targetX + stageWidth > screen.getMaxX()) {
            targetX = screen.getMaxX() - stageWidth - margin;
        }

        double targetY = ownerY + (ownerHeight - stageHeight) / 2.0;
        targetY = Math.max(screen.getMinY() + margin, Math.min(targetY, screen.getMaxY() - stageHeight - margin));

        stage.setX(targetX);
        stage.setY(targetY);
    }
    
    private VBox crearBotonera() {
        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 6 12; -fx-font-size: 11px;");
        btnConfirmar.setOnAction(e -> alConfirmar());
        btnConfirmar.setMaxWidth(Double.MAX_VALUE);
        
        VBox botonera = new VBox(8);
        botonera.getChildren().add(btnConfirmar);
        
        if (mostrarBotonCancelar) {
            Button btnCancelar = new Button("Cancelar");
            btnCancelar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-padding: 6 12; -fx-font-size: 11px;");
            btnCancelar.setOnAction(e -> alCancelar());
            btnCancelar.setMaxWidth(Double.MAX_VALUE);
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
        if (!confirmado) {
            limpiarSelecciones();
        }
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
