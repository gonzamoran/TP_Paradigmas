package edu.fiuba.algo3.vistas;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracionPartida extends VBox {

    private final Stage stage;
    private final VBox contenedorNombres; 
    private final Label errorLabel;
    private final List<TextField> listaDeInputs = new ArrayList<>(); 
    private int cantidadJugadores = 0;

    public ConfiguracionPartida(Stage stage) {
        this.stage = stage;

        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setStyle("-fx-padding: 30; -fx-background-color: #f0f2f5;");

        Label titulo = new Label("Configuración de Partida");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        titulo.getStyleClass().add("title"); 

        ComboBox<String> selectorCantidad = crearComboBoxCantidad();

        contenedorNombres = new VBox(15);
        contenedorNombres.setAlignment(Pos.CENTER);

        errorLabel = new Label("Debe completar todos los nombres para comenzar");
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setVisible(false);
        errorLabel.getStyleClass().add("error"); 

        Button btnJugar = crearBotonJugar();
        Button btnSalir = crearBotonSalir();
        HBox botonera = new HBox(20, btnJugar, btnSalir);
        botonera.setAlignment(Pos.CENTER);

        this.getChildren().addAll(titulo, new Label("Seleccione cantidad de jugadores:"), selectorCantidad, contenedorNombres, errorLabel, botonera);
    }


    private ComboBox<String> crearComboBoxCantidad() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("3 Jugadores", "4 Jugadores", "5 Jugadores", "6 Jugadores");
        comboBox.setPromptText("Seleccionar...");
        comboBox.getStyleClass().add("comboBox"); // 

        comboBox.setOnAction(e -> {
            this.cantidadJugadores = comboBox.getSelectionModel().getSelectedIndex() + 3; 
            generarCamposDeNombres(this.cantidadJugadores);
        });

        return comboBox;
    }

    private void generarCamposDeNombres(int cantidad) {
        contenedorNombres.getChildren().clear(); 
        listaDeInputs.clear(); 
        errorLabel.setVisible(false);

        for (int i = 0; i < cantidad; i++) {
            
            Label labelNombre = new Label("Nombre Jugador " + (i + 1) + ":");
            labelNombre.getStyleClass().add("labelText"); 

            TextField input = new TextField();
            input.setPromptText("Ej. Jugador " + (i+1));
            input.getStyleClass().add("textField");
            
            listaDeInputs.add(input);

            HBox fila = new HBox(10, labelNombre, input);
            fila.setAlignment(Pos.CENTER);
            
            contenedorNombres.getChildren().add(fila);
        }
    }

    private Button crearBotonJugar() {
        Button btn = new Button("¡Jugar Catan!");
        btn.getStyleClass().add("startButton"); 
        btn.setStyle("-fx-font-size: 14px; -fx-base: #4CAF50; -fx-cursor: hand;");
        
        btn.setOnAction(e -> {
            if (validarDatos()) {
                System.out.println("Iniciando juego con " + cantidadJugadores + " jugadores.");

                List<String> nombresJugadores = new ArrayList<>();
                for(TextField txt : listaDeInputs) {
                    nombresJugadores.add(txt.getText());
                }
                Juego.show(stage, nombresJugadores);
            }
        });
        return btn;
    }


    private boolean validarDatos() {
        if (cantidadJugadores == 0) {
            errorLabel.setText("¡Seleccione la cantidad de jugadores primero!");
            errorLabel.setVisible(true);
            return false;
        }
        for (TextField input : listaDeInputs) {
            if (input.getText().trim().isEmpty()) {
                errorLabel.setText("Todos los jugadores deben tener un nombre.");
                errorLabel.setVisible(true);
                return false;
            }
        }
        return true;
    }

    private Button crearBotonSalir() {
        Button btn = new Button("Salir");
        btn.getStyleClass().add("exitButton");
        btn.setOnAction(e -> Platform.exit());
        return btn;
    }
    
    public static void show(Stage stage) {
        ConfiguracionPartida root = new ConfiguracionPartida(stage);
        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Configuración de Partida - Catan");
        stage.setScene(scene);
        stage.show();
    }
}
