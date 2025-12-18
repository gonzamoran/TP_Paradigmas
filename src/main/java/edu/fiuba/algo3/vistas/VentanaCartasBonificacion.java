package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.ContextoCartaDesarrollo;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.GestorDeTurnos;
import edu.fiuba.algo3.controllers.ReproductorDeSonido;

import edu.fiuba.algo3.modelo.tablero.Coordenadas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Optional;

public class VentanaCartasBonificacion extends VBox {

    private final GestorDeTurnos gestor;
    private final FlowPane panelCartas;

    public VentanaCartasBonificacion(Stage stage, String nombreJugador, GestorDeTurnos gestor) {
        this.gestor = gestor;

        this.panelCartas = new FlowPane();
        this.panelCartas.setHgap(20);
        this.panelCartas.setVgap(20);
        this.panelCartas.setAlignment(Pos.CENTER);

        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setPadding(new Insets(30));

        ScrollPane scroll = new ScrollPane(panelCartas);
        scroll.setFitToWidth(true);

        scroll.setPrefHeight(400);
        scroll.setStyle("-fx-background: #2c3e50; -fx-border-color: transparent; -fx-background-color: transparent;");

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle(
                "-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 14px; -fx-padding: 10 20;");
        btnCerrar.setOnAction(e -> {
            ReproductorDeSonido.getInstance().playClick();
            stage.close();
        });
        this.getChildren().addAll(scroll, btnCerrar);

        var nombresCartas = new ArrayList<>(List.of("granRuta", "granCaballeria"));
        var cartasBonificacion = new ArrayList<VBox>();
        if (gestor.poseeGranRutaJugadorActual()) {
            var cartaRuta = crearCartaVisual(nombresCartas.get(0));
            cartasBonificacion.add(cartaRuta);
        }
        if (gestor.poseeGranCaballeriaJugadorActual()) {
            var cartaCaballeria = crearCartaVisual(nombresCartas.get(1));
            cartasBonificacion.add(cartaCaballeria);
        }
        if (cartasBonificacion.isEmpty()) {
            Label noCartasLabel = new Label("No posees cartas de bonificación.");
            noCartasLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
            panelCartas.getChildren().add(noCartasLabel);
            return;
        }
        panelCartas.getChildren().addAll(cartasBonificacion);
    }

    private VBox crearCartaVisual(String nombreCartaBonificacion) {
        VBox carta = new VBox(10);
        carta.setAlignment(Pos.CENTER);
        carta.setPrefSize(150, 220);

        String ruta = "/imagenes/" + nombreCartaBonificacion + ".png";
        InputStream is = getClass().getResourceAsStream(ruta);

        var jugadorActual = gestor.obtenerJugadorActual();
        if (gestor.poseeGranCaballeriaJugadorActual()) {
            Image img = new Image(is);
            ImageView view = new ImageView(img);
            view.setFitHeight(140);
            view.setPreserveRatio(true);
            carta.getChildren().add(view);
        }
        if (gestor.poseeGranRutaJugadorActual()) {
            Image img = new Image(is);
            ImageView view = new ImageView(img);
            view.setFitHeight(140);
            view.setPreserveRatio(true);
            carta.getChildren().add(view);
        }
        return carta;
    }

}