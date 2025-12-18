package edu.fiuba.algo3.vistas.ventanas;

import edu.fiuba.algo3.modelo.cartas.tiposDeCartaDesarrollo.CartasDesarrollo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import edu.fiuba.algo3.controllers.ReproductorDeSonido;

import java.io.InputStream;

public class VentanaMostrarCarta {
    protected final Stage stage;

    public VentanaMostrarCarta(Stage stage, CartasDesarrollo carta) {
        if (stage == null) {
            this.stage = new Stage();
        } else {
            this.stage = stage;
        }
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setTitle("Carta comprada");

        VBox root = new VBox(16);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        String nombre = nombreCarta(carta);
        String ruta = "/resources/imagenes/" + nombre + ".png";
        InputStream is = getClass().getResourceAsStream(ruta);

        if (is != null) {
            try {
                Image img = new Image(is);
                ImageView iv = new ImageView(img);
                iv.setFitHeight(300);
                iv.setPreserveRatio(true);
                root.getChildren().add(iv);
            } catch (Exception ex) {
                Label fallback = new Label(nombre);
                root.getChildren().add(fallback);
            }
        } else {
            Label fallback = new Label(nombre);
            root.getChildren().add(fallback);
        }

        Button btnOk = new Button("Aceptar");
        btnOk.setOnAction(e -> {
            ReproductorDeSonido.getInstance().playClick();
            stage.close();
        });
        root.getChildren().add(btnOk);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private String nombreCarta(CartasDesarrollo carta) {
        String cls = carta.getClass().getSimpleName();
        if (cls.contains("Caballero"))
            return "Caballero";
        if (cls.contains("Monopolio"))
            return "Monopolio";
        if (cls.contains("ConstruccionCarretera"))
            return "Construccion";
        if (cls.contains("PuntoVictoria"))
            return "PV";
        if (cls.contains("Descubrimiento"))
            return "Descubrimiento";
        return cls;
    }
}
