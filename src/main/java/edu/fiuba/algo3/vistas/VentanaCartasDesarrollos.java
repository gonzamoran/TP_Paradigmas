package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.cartas.CartasJugador;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartaPuntoVictoria;
import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.ContextoCartaDesarrollo;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;

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

    private ArrayList<CartasDesarrollo> cartasDesarrollo;

    public VentanaCartasDesarrollos(Stage stage, String nombreJugador) {
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

        List<String> cartas = generarManoAleatoria();


        if (cartas.isEmpty()) {
            Label vacio = new Label("No tienes recursos.");
            vacio.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 16px;");
            panelCartas.getChildren().add(vacio);
        } else {
            for (String nombreCarta : cartas) {
                //agregarCartaDesarrollo(CartasDesarrollo cartas)
                panelCartas.getChildren().add(crearCartaVisual(nombreCarta));
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

    private VBox crearCartaVisual(String nombreCartaDesarrollo) {
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

        return carta;
    }

    private List<String> generarManoAleatoria() {
        List<String> mano = new ArrayList<>();
        String[] tipos = {"Caballero", "Monopolio", "Construccion", "PV", "Descubrimiento"};
        Random rand = new Random();
        int cant = rand.nextInt(6) + 1;
        for (int i = 0; i < cant; i++) mano.add(tipos[rand.nextInt(tipos.length)]);
        return mano;
    }
}