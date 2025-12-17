package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.GestorDeTurnos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class VentanaFinJuego extends VBox {

    public VentanaFinJuego(String nombreGanador){

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(40));
        this.setSpacing(20);

        this.setStyle("-fx-background-color: #2x3e50; -fx-background-radius: 15; -fx-broder-radius: 15; -fx-border-color: #f1c40f; -fx-border-width: 3;");

        this.setMaxSize(400, 300);

        Label lblTitulo = new Label("VICTORIA!!!");
        lblTitulo.setStyle("-fx-font-size; 36px; -fx-font-weight: bold; -fx-text-fill: #f1c40f;");

        Label lblMensaje = new Label("El ganador es:...");
        lblMensaje.setStyle("-fx-font-size: 16px;-fx-text-fill: #bdc3c7;");


        Label lblGanador = new Label(nombreGanador);
        lblGanador.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button btnSalir = new Button("Salir");

        btnSalir.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 10 30");
        btnSalir.setOnAction(e -> System.exit(0));

        this.getChildren().addAll(lblTitulo,lblMensaje, lblGanador, btnSalir);
    }
}
