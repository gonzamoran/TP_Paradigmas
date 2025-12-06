package edu.fiuba.algo3.vistas;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

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

        contenedorDados.getChildren().add(crearDado(valor1));
        contenedorDados.getChildren().add(crearDado(valor2));

        Label total = new Label("Total: " + (valor1 + valor2));
        total.setStyle("-fx-font-size: 16px;");

        this.getChildren().addAll(titulo, contenedorDados, total);
    }

    //tiene que mostrar el resultado de los dados del gestor, no uno random
    private Node crearDado(int valor) {
        try {
            String ruta = "algo3/imagenes/dado" + valor + ".png";
            Image imagen = new Image(Objects.requireNonNull(getClass().getResourceAsStream(ruta)));
            
            ImageView imageView = new ImageView(imagen);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(100);
            return imageView;
        } catch (Exception e) {
            return crearDadoConNumero(valor);
        }
    }
    
    private StackPane crearDadoConNumero(int valor) {
        StackPane dado = new StackPane();
        
        Rectangle fondo = new Rectangle(100, 100);
        fondo.setFill(Color.WHITE);
        fondo.setStroke(Color.BLACK);
        fondo.setStrokeWidth(3);
        fondo.setArcWidth(15);
        fondo.setArcHeight(15);
        
        Label numero = new Label(String.valueOf(valor));
        numero.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        dado.getChildren().addAll(fondo, numero);
        return dado;
    }
}
