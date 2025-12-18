package edu.fiuba.algo3.vistas.ventanas;

import edu.fiuba.algo3.controllers.ReproductorDeSonido;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class VentanaVictoria {

    private final Label labelGanador;
    private final Stage ventanaDeLaVictoria;

    public VentanaVictoria() {

        this.ventanaDeLaVictoria = new Stage();
        this.ventanaDeLaVictoria.initModality(Modality.APPLICATION_MODAL);
        this.ventanaDeLaVictoria.setTitle("Juego Finalizado");
        this.ventanaDeLaVictoria.setMinWidth(200);

        this.labelGanador = new Label();
        labelGanador.getStyleClass().add("labelGanador");
        labelGanador.setPadding(new Insets(10, 0, 0, 0));

        Image img = new Image("ganador.png");
        ImageView view = new ImageView(img);

        ScaleTransition anim = new ScaleTransition();
        anim.setDuration(Duration.seconds(2));
        anim.setNode(view);
        anim.setByX(1.5);
        anim.setByY(1.5);
        anim.setCycleCount(Animation.INDEFINITE);
        anim.setAutoReverse(true);
        anim.play();

        view.setPreserveRatio(true);
        view.setFitWidth(this.ventanaDeLaVictoria.getWidth());
        view.setFitHeight(this.ventanaDeLaVictoria.getHeight());

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);

        Button exitButton = new Button();
        exitButton.setText("Salir");
        exitButton.getStyleClass().add("exitButton");
        exitButton.setOnAction(e -> {
            ReproductorDeSonido.getInstance().playClick();
            Platform.exit();
        });

        vBox.getChildren().addAll(view, labelGanador, exitButton);
        Scene scene = new Scene(vBox, 800, 800);

        this.ventanaDeLaVictoria.setScene(scene);

        this.ventanaDeLaVictoria.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });
    }

    public void mostrar() {
        this.ventanaDeLaVictoria.show();
        ReproductorDeSonido.getInstance().playVictoria();
    }

    public void actualizarGanador(String nombreGanador) {
        this.labelGanador.setText("¡" + nombreGanador + " ha ganado!");
        this.labelGanador.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: gold;");
    }
}
