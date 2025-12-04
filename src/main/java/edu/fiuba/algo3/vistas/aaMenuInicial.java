package edu.fiuba.algo3.vistas;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class aaMenuInicial extends VBox {
    
    public aaMenuInicial(Stage stage) {
        // Configuración visual
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        
        // Elementos
        Label titulo = new Label("Bienvenido a C.A.T.A.N.!");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Button btnJugar = new Button("Jugar");
        Button btnSalir = new Button("Salir");
        
        // Acciones
        btnJugar.setOnAction(e -> {
            System.out.println("Abriendo configuración...");
            aaConfiguracionPartida.show(stage);
        });
        
        btnSalir.setOnAction(e -> stage.close());
        
        // Agregar al layout
        this.getChildren().addAll(titulo, btnJugar, btnSalir);
    }
}