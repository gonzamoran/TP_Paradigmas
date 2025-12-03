
package edu.fiuba.algo3.vistas;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Juego {

    private static Label labelPuntos;
    private static Label labelNombreTurno;
    private static Label labelNombreFase;

    private static Label labelTituloInventario;
    private static Label lblMadera, lblLadrillo, lblLana, lblGrano, lblMineral;

    public static void show(Stage stage) {
        show(stage, new ArrayList<>());
    }

    public static void show(Stage stage, List<String> playerNames) {
        BorderPane root = new BorderPane();
        Image fondo = new Image("/hellofx/catan2.png");
        BackgroundImage backgroundImage = new BackgroundImage(
                    fondo,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(180, 180, true, true, true, true)
            );
            root.setBackground(new Background(backgroundImage));

        //BARRA SUPERIOR
        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");

        // IZQUIERDA
        HBox listaJugadores = new HBox(10);
        listaJugadores.setAlignment(Pos.CENTER_LEFT);
        if (playerNames != null && !playerNames.isEmpty()) {
            for (String nombre : playerNames) {
                Button p = new Button(nombre);
                p.setStyle("-fx-font-size: 12px; -fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50;");
                listaJugadores.getChildren().add(p);
            }
        }

        // DERECHA
        HBox indicadoresPanel = new HBox(15);
        indicadoresPanel.setAlignment(Pos.CENTER_RIGHT);

        String primerJugador = (playerNames != null && !playerNames.isEmpty()) ? playerNames.get(0) : "Esperando...";

        VBox boxTurno = crearIndicadorHUD("TURNO", primerJugador, "#f1c40f");
        VBox boxFase = crearIndicadorHUD("FASE", "Construcción", "#e67e22");

        //para actualizaciones futuras
        labelNombreTurno = (Label) boxTurno.getChildren().get(1);
        labelNombreFase = (Label) boxFase.getChildren().get(1);

        indicadoresPanel.getChildren().addAll(boxTurno, boxFase);

        topBar.setLeft(listaJugadores);
        topBar.setRight(indicadoresPanel);
        root.setTop(topBar);

        VBox panelInventario = crearPanelInventario(primerJugador);

        //PANEL DERECHO
        VBox rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));
        rightPanel.setAlignment(Pos.TOP_CENTER);
        rightPanel.setStyle("-fx-background-color: rgba(0,0,0,0.2); -fx-padding: 10;");
        rightPanel.setMinWidth(200);

        VBox boxPuntos = crearIndicadorHUD("PUNTOS VICTORIA", "0", "#3498db");
        labelPuntos = (Label) boxPuntos.getChildren().get(1);

        Button btnPoblado = crearBotonAccion("Construir Poblado", "#27ae60");
        Button btnCarretera = crearBotonAccion("Construir Carretera", "#27ae60");
        Button btnComerciar = crearBotonAccion("Comerciar", "#3498db");

        btnPoblado.setOnAction(e -> System.out.println("Acción: Construir Poblado"));
        btnCarretera.setOnAction(e -> System.out.println("Acción: Construir Carretera"));
        btnComerciar.setOnAction(e -> System.out.println("Acción: Comerciar"));

        VBox botonesBox = new VBox(8, btnPoblado, btnCarretera, btnComerciar);
        botonesBox.setAlignment(Pos.TOP_CENTER);

        rightPanel.getChildren().addAll(boxPuntos, botonesBox);

        //PANEL DERECHO
        VBox rightContainer = new VBox(12, panelInventario, rightPanel);
        rightContainer.setAlignment(Pos.TOP_CENTER);
        rightContainer.setPadding(new Insets(10));
        rightContainer.setMinWidth(220);
        root.setRight(rightContainer);

        //CENTRO
        StackPane centerPane = new StackPane();
        centerPane.setPadding(new Insets(20));

        //agregar logica del tablero acca

        root.setCenter(centerPane);

        //BARRA INFERIOR
        HBox bottomBar = new HBox();
        bottomBar.setPadding(new Insets(20));
        bottomBar.setAlignment(Pos.CENTER);
        bottomBar.setSpacing(20);
        bottomBar.setStyle("-fx-background-color: linear-gradient(to top, rgba(0,0,0,0.6), transparent);");

        Button botonDados = new Button("Tirar Dados");
        botonDados.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-base: #ffcc00; -fx-cursor: hand; -fx-font-weight: bold;");
        botonDados.setOnAction(e -> abrirVentanaDados());

        Button botonBaraja = new Button("Ver Baraja");
        botonBaraja.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-base: #3498db; -fx-cursor: hand; -fx-font-weight: bold;");
        botonBaraja.setOnAction(e -> abrirVentanaBaraja());

        Button botonDesarrollo = new Button("Ver Cartas Desarrollo");
        botonDesarrollo.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-base: #3498db; -fx-cursor: hand; -fx-font-weight: bold;");
        botonDesarrollo.setOnAction(e -> abrirVentanaDesarrollo());

        //agrego botones
        bottomBar.getChildren().addAll(botonBaraja, botonDados, botonDesarrollo);

        root.setBottom(bottomBar);
        root.setBottom(bottomBar);

        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("C.A.T.A.N. - Tablero Principal");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private static VBox crearIndicadorHUD(String titulo, String valorInicial, String colorBorde) {
        VBox box = new VBox(2);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(5, 15, 5, 15));

        box.setStyle("-fx-background-color: rgba(44, 62, 80, 0.9); " +
                "-fx-border-color: " + colorBorde + "; " +
                "-fx-border-width: 0 0 3 0; " +
                "-fx-background-radius: 5;");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setTextFill(Color.web("#bdc3c7"));
        lblTitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 10));

        Label lblValor = new Label(valorInicial);
        lblValor.setTextFill(Color.WHITE);
        lblValor.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        box.getChildren().addAll(lblTitulo, lblValor);
        return box;
    }

    private static Button crearBotonAccion(String texto, String colorHex) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: " + colorHex + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8 12;");
        VBox.setMargin(btn, new Insets(4, 0, 0, 0));
        return btn;
    }

    private static VBox crearPanelInventario(String nombreJugador) {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(15));
        panel.setStyle("-fx-background-color: rgba(20, 20, 20, 0.85); -fx-background-radius: 10; -fx-border-color: #7f8c8d; -fx-border-radius: 10;");
        panel.setMinWidth(160);

        // Inventario
        labelTituloInventario = new Label("Inventario (" + nombreJugador + ")");
        labelTituloInventario.setTextFill(Color.WHITE);
        labelTituloInventario.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        labelTituloInventario.setWrapText(true);
        labelTituloInventario.setMaxWidth(140);

        
        Label separador = new Label("________________");
        separador.setTextFill(Color.GRAY);

        // Filas de Recursos
        HBox filaMadera = crearFilaRecurso("Madera", "#2ecc71");
        lblMadera = (Label) filaMadera.getChildren().get(2); 

        HBox filaLadrillo = crearFilaRecurso("Ladrillo", "#e74c3c"); 
        lblLadrillo = (Label) filaLadrillo.getChildren().get(2);

        HBox filaLana = crearFilaRecurso("Lana", "#ecf0f1"); 
        lblLana = (Label) filaLana.getChildren().get(2);

        HBox filaGrano = crearFilaRecurso("Grano", "#f1c40f"); 
        lblGrano = (Label) filaGrano.getChildren().get(2);

        HBox filaMineral = crearFilaRecurso("Mineral", "#95a5a6");
        lblMineral = (Label) filaMineral.getChildren().get(2);

        panel.getChildren().addAll(labelTituloInventario, separador, filaMadera, filaLadrillo, filaLana, filaGrano, filaMineral);
        return panel;
    }

    private static HBox crearFilaRecurso(String nombre, String colorWeb) {
        HBox fila = new HBox();
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setSpacing(10); 

        Label colorBox = new Label("■");
        colorBox.setTextFill(Color.web(colorWeb));
        colorBox.setFont(Font.font("Arial", 18));

        Label lblNombre = new Label(nombre);
        lblNombre.setTextFill(Color.web(colorWeb));
        lblNombre.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblNombre.setMinWidth(60);

        Label lblCantidad = new Label("0");
        lblCantidad.setTextFill(Color.WHITE);
        lblCantidad.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        HBox info = new HBox(5, colorBox, lblNombre);
        info.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        fila.getChildren().addAll(info, spacer, lblCantidad);
        return fila;
    }

    private static void abrirVentanaBaraja(){
        Stage stageBaraja = new Stage();
        String nombreJugador = (labelNombreTurno != null) ? labelNombreTurno.getText() : "Jugador";
        VentanaBaraja ventanaBaraja = new VentanaBaraja(stageBaraja, nombreJugador);

        Scene scene = new Scene(ventanaBaraja, 600, 500);

        stageBaraja.setTitle("Baraja de Cartas");
        stageBaraja.setScene(scene);
        stageBaraja.show();
    }

    private static void abrirVentanaDesarrollo(){
        Stage stageDesarrollo = new Stage();
        String nombreJugador = (labelNombreTurno != null) ? labelNombreTurno.getText() : "Jugador";
        VentanaCartasDesarrollos ventanaDesarrollos = new VentanaCartasDesarrollos(stageDesarrollo, nombreJugador);

        Scene scene = new Scene(ventanaDesarrollos, 600, 500);

        stageDesarrollo.setTitle("Cartas Desarrollo");
        stageDesarrollo.setScene(scene);
        stageDesarrollo.show();
            }

    private static void abrirVentanaDados() {
        Stage stageDados = new Stage();
        Random rand = new Random();
        int dado1 = rand.nextInt(6) + 1;
        int dado2 = rand.nextInt(6) + 1;

        VistaDados vista = new VistaDados(dado1, dado2);

        Scene scene = new Scene(vista, 300, 250);
        stageDados.setTitle("Dados");
        stageDados.setScene(scene);
        stageDados.show();
    }
        //para actualizar indicadores desde la lógica del juego

    public static void actualizarInventario(String nombreJugador, int madera, int ladrillo, int lana, int grano, int mineral) {
        if (labelTituloInventario != null) labelTituloInventario.setText("Inventario (" + nombreJugador + ")");
        if (lblMadera != null) lblMadera.setText(String.valueOf(madera));
        if (lblLadrillo != null) lblLadrillo.setText(String.valueOf(ladrillo));
        if (lblLana != null) lblLana.setText(String.valueOf(lana));
        if (lblGrano != null) lblGrano.setText(String.valueOf(grano));
        if (lblMineral != null) lblMineral.setText(String.valueOf(mineral));
    }

    public static void actualizarPuntos(int puntos) {
        if (labelPuntos != null) labelPuntos.setText(String.valueOf(puntos));
    }

    public static void actualizarFase(String nuevaFase) {
        if (labelNombreFase != null) labelNombreFase.setText(nuevaFase);
    }

    public static void actualizarTurno(String nombreJugador) {
        if (labelNombreTurno != null) labelNombreTurno.setText(nombreJugador);
    }
}
