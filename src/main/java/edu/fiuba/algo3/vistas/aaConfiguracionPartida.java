package edu.fiuba.algo3.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class aaConfiguracionPartida extends VBox {
    
    public aaConfiguracionPartida(Stage stage) {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        
        Label titulo = new Label("Configuración de Partida");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        Button btnIniciar = new Button("Iniciar Juego");
        Button btnVolver = new Button("Volver");
        
        btnIniciar.setOnAction(e -> {
            System.out.println("Iniciando juego...");
            // Aquí irá la lógica para cambiar a Juego
        });
        
        btnVolver.setOnAction(e -> {
            // Volver al menú principal
            aaMenuInicial menu = new aaMenuInicial(stage);
            stage.setScene(new Scene(menu, 400, 300));
        });
        
        this.getChildren().addAll(titulo, btnIniciar, btnVolver);
    }
    
    // Método estático para mostrar (como en el original)
    public static void show(Stage stage) {
        aaConfiguracionPartida config = new aaConfiguracionPartida(stage);
        Scene escena = new Scene(config, 600, 400);
        stage.setScene(escena);
    }
}