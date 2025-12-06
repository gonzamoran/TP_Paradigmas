package edu.fiuba.algo3.vistas;
import java.util.concurrent.CompletableFuture;

import edu.fiuba.algo3.modelo.ProveedorDeDatos;
import edu.fiuba.algo3.modelo.tablero.Coordenadas;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class VentanaConstruirCarretera extends VBox {
    
    private CompletableFuture<Coordenadas[]> resultadoFuturo;
    private Stage stage;

    public VentanaConstruirCarretera(Stage stage, String nombreJugador) {
        this.stage = stage;
        stage.setTitle("Construir Carretera");

        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2c3e50; -fx-min-width: 400;");


        Label lblTitulo = new Label("Construcción de Poblado");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label lblJugador = new Label("Jugador: " + nombreJugador);
        lblJugador.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");

        Label lblInstruccion = new Label("Seleccione las coordenadas del hexágono:");
        lblInstruccion.setStyle("-fx-font-size: 12px; -fx-text-fill: white;");

        TextField txtHexX = new TextField();
        txtHexX.setPromptText("X");
        txtHexX.setPrefWidth(60);
        txtHexX.setStyle("-fx-background-radius: 5;");

        TextField txtHexY = new TextField();
        txtHexY.setPromptText("Y");
        txtHexY.setPrefWidth(60);
        txtHexY.setStyle("-fx-background-radius: 5;");

        HBox boxCoords = new HBox(10, new Label("Hexágono (X, Y):"), txtHexX, txtHexY);
        boxCoords.setAlignment(Pos.CENTER);

        ((Label)boxCoords.getChildren().get(0)).setStyle("-fx-text-fill: white;");

 
        Button btnConstruir = new Button("Construir");
        btnConstruir.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        
        Button btnCancelar = new Button("Cancelar");
        btnCancelar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-cursor: hand;");

        HBox boxBotones = new HBox(20, btnConstruir, btnCancelar);
        boxBotones.setAlignment(Pos.CENTER);


        btnConstruir.setOnAction(e -> {
            try {

                String xStr = txtHexX.getText();
                String yStr = txtHexY.getText();


                int x = Integer.parseInt(xStr);
                int y = Integer.parseInt(yStr);

                Coordenadas coord = new Coordenadas(x, y);
                
                if (resultadoFuturo != null) {
                    resultadoFuturo.complete(new Coordenadas[]{coord, coord});
                }
                
                System.out.println("Construyendo poblado para " + nombreJugador + 
                                   " en Hex[" + x + "," + y + "]");
                
                stage.close();

            } catch (NumberFormatException ex) {
                mostrarAlerta("Error de Formato", "Las coordenadas deben ser números enteros.");
            }
        });

        btnCancelar.setOnAction(e -> stage.close());

        root.getChildren().addAll(lblTitulo, lblJugador, lblInstruccion, boxCoords, boxBotones);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private static void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
    }
    public CompletableFuture<Coordenadas[]> solicitarCoordenadas() {
        resultadoFuturo = new CompletableFuture<>();
        stage.show();
        return resultadoFuturo;
    }
}