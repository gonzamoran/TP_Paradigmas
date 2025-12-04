package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.ProveedorDeDatos;
import edu.fiuba.algo3.modelo.tablero.Tablero;
import edu.fiuba.algo3.modelo.tablero.Hexagono;
import edu.fiuba.algo3.modelo.tablero.Produccion;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class CampoDeJuego extends BorderPane {

    private final Tablero tablero;
    private Hexagono hexagonoSeleccionado;
    private ProveedorDeDatos proveedor;
    private AdaptadorVistaJavaFX adaptador;

    public CampoDeJuego(Stage stage, Tablero tablero, ProveedorDeDatos proveedor) {
        this.tablero = tablero;
        this.proveedor = proveedor;
        
        // Crear el adaptador y registrarlo en el proveedor
        this.adaptador = new AdaptadorVistaJavaFX(stage);
        this.proveedor.setVista(adaptador);
        
        crearTablero();

        //this.setRight(this.menuLateralDerecho);
        
        //this.mostrarMenuLateralDerecho();
        stage.setScene(new Scene(this, 1500, 900));
        stage.centerOnScreen();
    }

    private void crearTablero() {
        Pane pane = new Pane();
        pane.getChildren().add(crearVistaImagen());

        //inicializarVertices();

        //asignarCoordenadasHexagonos();
        
        //asignarNumeroALosHexagonos();

        HBox anHbox = new HBox(pane);
        anHbox.setAlignment(Pos.CENTER);
        VBox aVbox = new VBox(anHbox);
        aVbox.setAlignment(Pos.CENTER);
        setMargin(this, new Insets(50,50,50,50));

        this.setCenter(aVbox);
    }



    private ImageView crearVistaImagen() {
        Image imagen = new Image("catan2.png");
        ImageView imageView = new ImageView(imagen);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(1000);
        imageView.setFitHeight(800);
        return imageView;
    }

    private void mostrarMenuLateralDerecho() {
        //this.menuLateralDerecho.mostrarInventario();
    }
}