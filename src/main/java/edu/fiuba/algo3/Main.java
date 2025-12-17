package edu.fiuba.algo3;

import edu.fiuba.algo3.controllers.ReproductorDeSonido;
import edu.fiuba.algo3.vistas.MenuInicial;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application{
    @Override
    public void start(Stage stage) {
        //ReproductorDeSonido.getInstance().playFondo();
        MenuInicial menuInicial = new MenuInicial();
        menuInicial.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
