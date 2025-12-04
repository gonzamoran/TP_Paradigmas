package edu.fiuba.algo3.vistas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class aaApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // 1. Crear el menú inicial
        aaMenuInicial menu = new aaMenuInicial(primaryStage);
        
        // 2. Crear escena con el menú
        Scene escena = new Scene(menu, 400, 300);
        
        // 3. Configurar la ventana
        primaryStage.setTitle("C.A.T.A.N.");
        primaryStage.setScene(escena);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}