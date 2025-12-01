
package edu.fiuba.algo3.vistas;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Juego {

    public static void show(Stage stage) {
        show(stage, new ArrayList<>());
    }

    public static void show(Stage stage, List<String> playerNames) {
        Image fondo = new Image(
                Juego.class.getResource("/hellofx/catan2.png").toExternalForm()
        );

        BackgroundImage backgroundImage = new BackgroundImage(
                fondo,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );

        BorderPane root = new BorderPane();
        root.setBackground(new Background(backgroundImage));

        if (playerNames != null && !playerNames.isEmpty()) {
            HBox jugadoresBox = new HBox(10);
            jugadoresBox.setAlignment(Pos.CENTER);
            jugadoresBox.setStyle("-fx-padding: 10; -fx-background-color: rgba(255,255,255,0.6);");
            for (String nombre : playerNames) {
                Button p = new Button(nombre);
                p.setStyle("-fx-font-size: 12px; -fx-padding: 6 10;");
                jugadoresBox.getChildren().add(p);
            }
            root.setTop(jugadoresBox);
        }

        Button botonDados = new Button("Tirar Dados");
        botonDados.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-base: #ffcc00; -fx-cursor: hand;");

        botonDados.setOnAction(e -> {
            abrirVentanaDados();
        });


        HBox panelBoton = new HBox(botonDados);
        panelBoton.setAlignment(Pos.CENTER);
        panelBoton.setStyle("-fx-padding: 20; -fx-background-color: rgba(0,0,0,0.3);"); 

        root.setBottom(panelBoton);

        Scene scene = new Scene(root, 800, 700);
        stage.setTitle("C.A.T.A.N. - Tablero Principal");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private static void abrirVentanaDados() {
        Stage stageDados = new Stage();
        

        Random rand = new Random();
        int dado1 = rand.nextInt(6) + 1;
        int dado2 = rand.nextInt(6) + 1;


        VistaDados vista = new VistaDados(dado1, dado2);

        Scene scene = new Scene(vista, 300, 250);
        stageDados.setTitle("Resultado Dados");
        stageDados.setScene(scene);
        
        stageDados.show();
    }
}
