package edu.fiuba.algo3.vistas;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuInicial extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        Label titleLabel = new Label("Bienvenido a C.A.T.A.N.!");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        Button botonJugar = new Button("Jugar");
        Button botonSalir = new Button("Salir");

        String buttonStyle = "-fx-font-size: 14px; -fx-min-width: 100px;";
        botonJugar.setStyle(buttonStyle);
        botonSalir.setStyle(buttonStyle);

        botonSalir.setOnAction(e -> {
            System.out.println("Cerrando el juego...");
            primaryStage.close();
        });

        botonJugar.setOnAction(e -> {
            System.out.println("Cambiando a la vista del juego...");
            ConfiguracionPartida.show(primaryStage);
        });

        VBox root = new VBox(20); 
        root.setAlignment(Pos.CENTER); 
        root.getChildren().addAll(titleLabel, botonJugar, botonSalir);

        Scene scene = new Scene(root, 400, 300);
        
        primaryStage.setTitle("Menú Principal - Catan");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
