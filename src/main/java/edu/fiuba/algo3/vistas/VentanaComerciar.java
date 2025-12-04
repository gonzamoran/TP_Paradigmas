package edu.fiuba.algo3.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VentanaComerciar extends VBox {

    public VentanaComerciar(Stage stage, String nombreJugador) {
        stage.setTitle("Comerciar");

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2c3e50; -fx-min-width: 400; -fx-min-height: 300;");

        mostrarMenuPrincipal(root, stage, nombreJugador);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private static void mostrarMenuPrincipal(VBox root, Stage stage, String nombreJugador) {
        root.getChildren().clear();

        Label lblTitulo = new Label("Menú de Comercio");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblSubtitulo = new Label("Jugador: " + nombreJugador);
        lblSubtitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");

        // Botón Comercio Interior
        Button btnInterior = new Button("Comercio Interior");
        estilarBoton(btnInterior, "#27ae60"); 
        btnInterior.setOnAction(e -> {

             //intercambiar(ArrayList<Recurso> recursosAEntregar, ArrayList<Recurso> recursosARecibir,Jugador jugador2)

            System.out.println("Iniciando Comercio Interior para " + nombreJugador);
            stage.close();
        });

        Button btnMaritimo = new Button("Comercio Marítimo");
        estilarBoton(btnMaritimo, "#3498db"); 
        btnMaritimo.setOnAction(e -> {
            mostrarOpcionesMaritimas(root, stage, nombreJugador);
        });

        Button btnCancelar = new Button("Cancelar");
        estilarBoton(btnCancelar, "#e74c3c"); 
        btnCancelar.setOnAction(e -> stage.close());

        root.getChildren().addAll(lblTitulo, lblSubtitulo, btnInterior, btnMaritimo, btnCancelar);
    }

    
    private static void mostrarOpcionesMaritimas(VBox root, Stage stage, String nombreJugador) {
        root.getChildren().clear(); 

        Label lblTitulo = new Label("Comercio Marítimo");
        lblTitulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblInstruccion = new Label("Seleccione la tasa de cambio:");
        lblInstruccion.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");

        
        Button btnEstandar = new Button("Tasa Estándar (4:1)");
        estilarBoton(btnEstandar, "#f39c12"); 
        btnEstandar.setOnAction(e -> {

            //comerciar(Jugador jugador, ArrayList<Recurso> oferta, Recurso demanda)

            System.out.println("Comercio 4:1 realizado por " + nombreJugador);
            stage.close();
        });

        Button btnGenerico = new Button("Puerto Genérico (3:1)");
        estilarBoton(btnGenerico, "#f39c12");
        btnGenerico.setOnAction(e -> {

             //comerciar(Jugador jugador, ArrayList<Recurso> oferta, Recurso demanda)

            System.out.println("Comercio 3:1 realizado por " + nombreJugador);
            stage.close();
        });

        Button btnEspecifico = new Button("Puerto Específico (2:1)");
        estilarBoton(btnEspecifico, "#f39c12");
        btnEspecifico.setOnAction(e -> {

             //comerciar(Jugador jugador, ArrayList<Recurso> oferta, Recurso demanda)

            System.out.println("Comercio 2:1 realizado por " + nombreJugador);
            stage.close();
        });

        Button btnVolver = new Button("Volver al Menú Anterior");
        estilarBoton(btnVolver, "#95a5a6"); 
        btnVolver.setOnAction(e -> mostrarMenuPrincipal(root, stage, nombreJugador));

        root.getChildren().addAll(lblTitulo, lblInstruccion, btnEstandar, btnGenerico, btnEspecifico, btnVolver);
    }

    private static void estilarBoton(Button btn, String colorHex) {
        btn.setStyle("-fx-background-color: " + colorHex + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-min-width: 250px; -fx-font-size: 14px;");
        VBox.setMargin(btn, new Insets(5, 0, 5, 0)); 
    }
}