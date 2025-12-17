package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.ContextoCartaDesarrollo;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.GestorDeTurnos;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class VentanaCartasDesarrollos extends VBox {


    private final GestorDeTurnos gestor;

    public VentanaCartasDesarrollos(Stage stage, String nombreJugador, GestorDeTurnos gestor) {
        this.gestor = gestor;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20); 
        this.setPadding(new Insets(30)); 
        
        
        this.setStyle("-fx-background-color: #2c3e50; -fx-min-width: 600; -fx-min-height: 500;");

        Label titulo = new Label("Cartas Desarrollo de " + nombreJugador);
        
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        FlowPane panelCartas = new FlowPane();
        panelCartas.setHgap(20); 
        panelCartas.setVgap(20); 
        panelCartas.setAlignment(Pos.CENTER);

        // Obtener cartas reales del jugador actual mediante el gestor
        Jugador jugador = gestor != null ? gestor.obtenerJugadorActual() : null;

        if (jugador == null || jugador.obtenerCartasDeDesarrollo().isEmpty()) {
            Label vacio = new Label("No tienes cartas de desarrollo.");
            vacio.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 16px;");
            panelCartas.getChildren().add(vacio);
        } else {
            java.util.ArrayList<CartasDesarrollo> jugables = gestor.obtenerCartasJugablesJugadorActual();
            java.util.ArrayList<CartasDesarrollo> noJugables = gestor.obtenerCartasNoJugablesJugadorActual();

            for (CartasDesarrollo carta : jugables) {
                panelCartas.getChildren().add(crearCartaVisual(carta, true));
            }
            for (CartasDesarrollo carta : noJugables) {
                panelCartas.getChildren().add(crearCartaVisual(carta, false));
            }
        }

        ScrollPane scroll = new ScrollPane(panelCartas);
        scroll.setFitToWidth(true);
        
        scroll.setPrefHeight(400); 
        scroll.setStyle("-fx-background: #2c3e50; -fx-border-color: transparent; -fx-background-color: transparent;");

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand; -fx-font-size: 14px; -fx-padding: 10 20;");
        btnCerrar.setOnAction(e -> stage.close());

        this.getChildren().addAll(titulo, scroll, btnCerrar);
    }

    private VBox crearCartaVisual(CartasDesarrollo cartaModelo, boolean jugable) {
        String nombreCartaDesarrollo = obtenerNombreCarta(cartaModelo);
        VBox carta = new VBox(10);
        carta.setAlignment(Pos.CENTER);
        
        carta.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 8, 0, 0, 0);");
        
        carta.setPrefSize(150, 220); 

        String ruta = "/hellofx/" + nombreCartaDesarrollo + ".png";
        InputStream is = getClass().getResourceAsStream(ruta);
        
        if (is != null) {
            try {
                Image img = new Image(is);
                ImageView view = new ImageView(img);
                
                
                view.setFitHeight(140); 
                view.setPreserveRatio(true);
                
                carta.getChildren().add(view);
            } catch (Exception ex) {
                Label lblFalla = new Label(nombreCartaDesarrollo.substring(0, 1));
                lblFalla.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #7f8c8d;");
                carta.getChildren().add(lblFalla);
            }
        } else {
            
            Label lblFalla = new Label(nombreCartaDesarrollo.substring(0, 1));
            lblFalla.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: #7f8c8d;");
            carta.getChildren().add(lblFalla);
        }

        Label lblNombre = new Label(nombreCartaDesarrollo);
        lblNombre.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333;");
        carta.getChildren().add(lblNombre);

        // Si la carta NO es jugable, desaturamos/grisamos visualmente
        if (!jugable) {
            carta.setOpacity(0.45);
            carta.setDisable(true);
        } else {
            carta.setOpacity(1.0);
            carta.setDisable(false);
        }

        return carta;
    }

    private String obtenerNombreCarta(CartasDesarrollo carta) {
        String cls = carta.getClass().getSimpleName();
        if (cls.contains("Caballero")) return "Caballero";
        if (cls.contains("Monopolio")) return "Monopolio";
        if (cls.contains("ConstruccionCarretera")) return "Construccion";
        if (cls.contains("PuntoVictoria")) return "PV";
        if (cls.contains("Descubrimiento")) return "Descubrimiento";
        return cls;
    }

    // método anterior eliminado: la vista usa las cartas reales del jugador
}