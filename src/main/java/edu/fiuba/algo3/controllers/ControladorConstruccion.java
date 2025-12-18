package edu.fiuba.algo3.controllers;

import edu.fiuba.algo3.modelo.GestorDeTurnos;
import edu.fiuba.algo3.modelo.excepciones.RecursosInsuficientesException;
import edu.fiuba.algo3.vistas.TableroUI;
import edu.fiuba.algo3.vistas.ventanas.VentanaComerciar;
import edu.fiuba.algo3.vistas.ventanas.VentanaConstruirCarretera;
import edu.fiuba.algo3.vistas.ventanas.VentanaConstruirPoblado;
import edu.fiuba.algo3.vistas.ventanas.VentanaMejorarPoblado;
import edu.fiuba.algo3.vistas.ventanas.VentanaMostrarCarta;
import edu.fiuba.algo3.vistas.ventanas.VentanaVictoria;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControladorConstruccion {

    private final GestorDeTurnos gestor;
    private final String nombreJugador;
    private TableroUI tableroUI;
    private Button btnPoblado;
    private Button btnCarretera;
    private Button btnComerciar;
    private Button btnComprarCartaDesarrollo;
    private Button btnMejorarPoblado;

    private Stage ventanaConstruirPoblado;
    private Stage ventanaConstruirCarretera;
    private Stage ventanaComerciar;
    private Stage ventanaMejorarPoblado;
    private Runnable onVentanaComerciarCerrada;
    private Runnable onModalCerrada;

    public ControladorConstruccion(GestorDeTurnos gestor, String nombreJugador) {
        this.gestor = gestor;
        this.nombreJugador = nombreJugador;
    }

    public void setTableroUI(TableroUI tableroUI) {
        this.tableroUI = tableroUI;
    }

    public void setOnVentanaComerciarCerrada(Runnable callback) {
        this.onVentanaComerciarCerrada = callback;
    }

    public void setOnModalCerrada(Runnable callback) {
        this.onModalCerrada = callback;
    }

    public VBox crearPanelBotonesConstruccion() {
        btnPoblado = crearBotonAccion("Construir Poblado", "#27ae60");
        btnCarretera = crearBotonAccion("Construir Carretera", "#27ae60");
        btnMejorarPoblado = crearBotonAccion("Mejorar Poblado", "#f39c12");
        btnComerciar = crearBotonAccion("Comerciar", "#3498db");
        btnComprarCartaDesarrollo = crearBotonAccion("Comprar carta desarrollo", "#9b59b6");

        btnPoblado.setOnAction(e -> abrirVentanaConstruirPoblado());
        btnCarretera.setOnAction(e -> abrirVentanaConstruirCarretera());
        btnMejorarPoblado.setOnAction(e -> abrirVentanaMejorarPoblado());
        btnComerciar.setOnAction(e -> abrirVentanaComerciar());
        btnComprarCartaDesarrollo.setOnAction(e -> {
            ReproductorDeSonido.getInstance().playClick();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Comprar carta de desarrollo");
            confirm.setHeaderText(null);
            confirm.setContentText("¿Deseas comprar una carta de desarrollo por los recursos correspondientes?");

            var resultado = confirm.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                try {
                    var cartaComprada = gestor.comprarCartaDesarrollo();
                    if (cartaComprada != null) {
                        Stage modal = new Stage();
                        VentanaMostrarCarta ventanaCarta = new VentanaMostrarCarta(modal, cartaComprada);
                    }
                    if (onModalCerrada != null)
                        onModalCerrada.run();
                } catch (RecursosInsuficientesException ex) {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Recursos insuficientes");
                    alerta.setHeaderText(null);
                    alerta.setContentText("No tienes los recursos necesarios para comprar una carta de desarrollo.");
                    alerta.showAndWait();
                }
            } else {
                if (onModalCerrada != null)
                    onModalCerrada.run();
            }
        });

        btnPoblado.setVisible(false);
        btnCarretera.setVisible(false);
        btnMejorarPoblado.setVisible(false);
        btnComerciar.setVisible(false);
        btnComprarCartaDesarrollo.setVisible(false);

        VBox botonesBox = new VBox(8, btnPoblado, btnCarretera, btnMejorarPoblado, btnComerciar,
                btnComprarCartaDesarrollo);
        botonesBox.setAlignment(Pos.TOP_CENTER);

        return botonesBox;
    }

    private Button crearBotonAccion(String texto, String colorHex) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: " + colorHex
                + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 12;");
        VBox.setMargin(btn, new Insets(4, 0, 0, 0));
        return btn;
    }

    private void abrirVentanaConstruirPoblado() {
        if (ventanaConstruirPoblado != null && ventanaConstruirPoblado.isShowing()) {
            ventanaConstruirPoblado.requestFocus();
            return;
        }
        ventanaConstruirPoblado = new Stage();
        ventanaConstruirPoblado.setOnCloseRequest(e -> {
            ventanaConstruirPoblado = null;
            if (onModalCerrada != null)
                onModalCerrada.run();
        });
        VentanaConstruirPoblado ventana = new VentanaConstruirPoblado(
                ventanaConstruirPoblado,
                nombreJugador,
                gestor,
                tableroUI,
                () -> {
                    if (onModalCerrada != null)
                        onModalCerrada.run();
                });
    }

    private void abrirVentanaConstruirCarretera() {
        if (ventanaConstruirCarretera != null && ventanaConstruirCarretera.isShowing()) {
            ventanaConstruirCarretera.requestFocus();
            return;
        }
        ventanaConstruirCarretera = new Stage();
        ventanaConstruirCarretera.setOnCloseRequest(e -> {
            ventanaConstruirCarretera = null;
            if (onModalCerrada != null)
                onModalCerrada.run();
        });
        VentanaConstruirCarretera ventana = new VentanaConstruirCarretera(
                ventanaConstruirCarretera,
                nombreJugador,
                gestor,
                tableroUI,
                () -> {
                    if (onModalCerrada != null)
                        onModalCerrada.run();
                });
    }

    private void abrirVentanaComerciar() {
        if (ventanaComerciar != null && ventanaComerciar.isShowing()) {
            ventanaComerciar.requestFocus();
            return;
        }
        ventanaComerciar = new Stage();

        ventanaComerciar.setOnCloseRequest(e -> {
            ventanaComerciar = null;
        });
        VentanaComerciar ventana = new VentanaComerciar(ventanaComerciar, nombreJugador, gestor, () -> {
            if (onVentanaComerciarCerrada != null)
                onVentanaComerciarCerrada.run();
        });
    }

    private void abrirVentanaMejorarPoblado() {
        if (ventanaMejorarPoblado != null && ventanaMejorarPoblado.isShowing()) {
            ventanaMejorarPoblado.requestFocus();
            return;
        }
        ventanaMejorarPoblado = new Stage();
        ventanaMejorarPoblado.setOnCloseRequest(e -> {
            ventanaMejorarPoblado = null;
            if (onModalCerrada != null)
                onModalCerrada.run();
        });
        VentanaMejorarPoblado ventana = new VentanaMejorarPoblado(
                ventanaMejorarPoblado,
                nombreJugador,
                gestor,
                tableroUI,
                () -> {
                    if (onModalCerrada != null)
                        onModalCerrada.run();
                });
    }

    public void mostrarVentanaVictoria() {
        VentanaVictoria ventana = new VentanaVictoria();
        ventana.mostrar();
    }

    public Button getBtnTerminarFase() {
        Button btn = crearBotonAccion("Terminar Fase", "#e67e22");
        return btn;
    }

    public void actualizarVisibilidadSegunFase(int indiceFase) {
        boolean enConstruccion = (indiceFase == 1);
        if (btnPoblado != null)
            btnPoblado.setVisible(enConstruccion);
        if (btnCarretera != null)
            btnCarretera.setVisible(enConstruccion);
        if (btnMejorarPoblado != null)
            btnMejorarPoblado.setVisible(enConstruccion);
        if (btnComerciar != null)
            btnComerciar.setVisible(enConstruccion);
        if (btnComprarCartaDesarrollo != null)
            btnComprarCartaDesarrollo.setVisible(enConstruccion);
    }
}
