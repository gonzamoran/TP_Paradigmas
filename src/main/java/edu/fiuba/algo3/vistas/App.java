package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.SystemInfo;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import edu.fiuba.algo3.vistas.MenuInicial;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final int ANCHO = 800;
    private static final int ALTO = 550;

    @Override
    public void start(Stage stage) {

        //sonido

        MenuInicial menuInicial = new MenuInicial(stage);
        Scene presentacion = new Scene(menuInicial, ANCHO, ALTO);

        stage.setScene(presentacion);
        stage.setTitle("C.A.T.A.N.");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
