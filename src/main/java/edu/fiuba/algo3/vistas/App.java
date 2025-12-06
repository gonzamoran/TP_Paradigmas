package edu.fiuba.algo3.vistas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

  private static final int ANCHO = 800;
  private static final int ALTO = 550;

  @Override
  public void start(Stage stage) {
    MenuInicial menuInicial = new MenuInicial();
    menuInicial.start(stage);
  }

  public static void main(String[] args) {
    launch();
  }
}
