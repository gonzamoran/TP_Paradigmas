package edu.fiuba.algo3.vistas;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.Objects;

public class VistaDados extends VBox {


    public VistaDados(int valor1, int valor2) {
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setStyle("-fx-padding: 20; -fx-background-color: white;");

        Label titulo = new Label("Resultado:");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");


        HBox contenedorDados = new HBox(15);
        contenedorDados.setAlignment(Pos.CENTER);


        contenedorDados.getChildren().add(crearImagenDado(valor1));
        contenedorDados.getChildren().add(crearImagenDado(valor2));

        Label total = new Label("Total: " + (valor1 + valor2));
        total.setStyle("-fx-font-size: 16px;");

        this.getChildren().addAll(titulo, contenedorDados, total);
    }

    private ImageView crearImagenDado(int valor) {
        try {

            String ruta = "/hellofx/dado" + valor + ".png";
            Image imagen = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ruta)));
            
            ImageView imageView = new ImageView(imagen);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(100);
            return imageView;
        } catch (Exception e) {
            System.out.println("No se encontró la imagen para el dado " + valor);
            return new ImageView();
        }
    }
}
